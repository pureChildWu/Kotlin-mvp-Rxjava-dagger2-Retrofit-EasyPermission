package com.gzinfo.kotlintiktok.util

import java.io.IOException
import java.io.InputStream
import java.security.KeyManagementException
import java.security.KeyStore
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.*

/**
 *@ClassName:HttpsUtils
 *@Author:CreatBy wlh
 *@Time:2020/7/24 14点
 *@Email:m15904921255@163.com
 *@Desc:TODO
 */
public class HttpsUtils {
    companion object{
        fun getSslSocketFactory(trustManager: X509TrustManager?, bksFile: InputStream, password: String, certificates: Array<out InputStream>?): SSLParams {
            val sslParams = SSLParams()
            return try {
                val keyManagers = prepareKeyManager(bksFile, password)
                val trustManagers = certificates?.let { prepareTrustManager(*it) }
                val manager: X509TrustManager
                manager = //优先使用用户自定义的TrustManager
                    trustManager
                        ?: (trustManagers?.let {
                            //然后使用默认的TrustManager
                            chooseTrustManager(it)
                        } ?: //否则使用不安全的TrustManager
                        UnSafeTrustManager)
                // 创建TLS类型的SSLContext对象， that uses our TrustManager
                val sslContext = SSLContext.getInstance("TLS")
                // 用上面得到的trustManagers初始化SSLContext，这样sslContext就会信任keyStore中的证书
                // 第一个参数是授权的密钥管理器，用来授权验证，比如授权自签名的证书验证。第二个是被授权的证书管理器，用来验证服务器端的证书
                sslContext.init(keyManagers, arrayOf<TrustManager?>(manager), null)
                // 通过sslContext获取SSLSocketFactory对象
                sslParams.sSLSocketFactory = sslContext.socketFactory
                sslParams.trustManager = manager
                sslParams
            } catch (e: NoSuchAlgorithmException) {
                throw AssertionError(e)
            } catch (e: KeyManagementException) {
                throw AssertionError(e)
            }
        }


        private fun prepareKeyManager(
            bksFile: InputStream?,
            password: String?
        ): Array<KeyManager>? {
            try {
                if (bksFile == null || password == null) return null
                val clientKeyStore = KeyStore.getInstance("BKS")
                clientKeyStore.load(bksFile, password.toCharArray())
                val kmf =
                    KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
                kmf.init(clientKeyStore, password.toCharArray())
                return kmf.keyManagers
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }


        private fun prepareTrustManager(vararg certificates: InputStream): Array<TrustManager>? {
            if (certificates == null || certificates.size <= 0) return null
            try {
                val certificateFactory =
                    CertificateFactory.getInstance("X.509")
                // 创建一个默认类型的KeyStore，存储我们信任的证书
                val keyStore =
                    KeyStore.getInstance(KeyStore.getDefaultType())
                keyStore.load(null)
                var index = 0
                for (certStream in certificates) {
                    val certificateAlias = Integer.toString(index++)
                    // 证书工厂根据证书文件的流生成证书 cert
                    val cert =
                        certificateFactory.generateCertificate(certStream)
                    // 将 cert 作为可信证书放入到keyStore中
                    keyStore.setCertificateEntry(certificateAlias, cert)
                    try {
                        certStream?.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
                //我们创建一个默认类型的TrustManagerFactory
                val tmf =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
                //用我们之前的keyStore实例初始化TrustManagerFactory，这样tmf就会信任keyStore中的证书
                tmf.init(keyStore)
                //通过tmf获取TrustManager数组，TrustManager也会信任keyStore中的证书
                return tmf.trustManagers
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        private fun chooseTrustManager(trustManagers: Array<TrustManager>): X509TrustManager? {
            for (trustManager in trustManagers) {
                if (trustManager is X509TrustManager) {
                    return trustManager
                }
            }
            return null
        }






        /**
         * 为了解决客户端不信任服务器数字证书的问题，网络上大部分的解决方案都是让客户端不对证书做任何检查，
         * 这是一种有很大安全漏洞的办法
         */
        var UnSafeTrustManager: X509TrustManager =
            object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<X509Certificate>,
                    authType: String
                ) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<X509Certificate>,
                    authType: String
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            }

        /**
         * 此类是用于主机名验证的基接口。 在握手期间，如果 URL 的主机名和服务器的标识主机名不匹配，
         * 则验证机制可以回调此接口的实现程序来确定是否应该允许此连接。策略可以是基于证书的或依赖于其他验证方案。
         * 当验证 URL 主机名使用的默认规则失败时使用这些回调。如果主机名是可接受的，则返回 true
         */
        var UnSafeHostnameVerifier =
            HostnameVerifier { hostname, session -> true }

    }

    class SSLParams {
        var sSLSocketFactory: SSLSocketFactory? = null
        var trustManager: X509TrustManager? = null
    }

}