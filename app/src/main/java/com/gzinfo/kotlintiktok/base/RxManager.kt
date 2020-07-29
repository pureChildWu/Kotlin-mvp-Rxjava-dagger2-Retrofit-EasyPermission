package com.gzinfo.kotlintiktok.base

import android.content.Context
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.CertificateFactory
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManagerFactory

/**
 *@ClassName:RxManager
 *@Author:CreatBy wlh
 *@Time:2020/7/24 16点
 *@Email:m15904921255@163.com
 *@Desc:TODO
 */
class RxManager {
    private var mDisposables // 管理订阅者者
            : CompositeDisposable? = null

    fun add(m: Disposable?) {
        if (mDisposables == null) {
            mDisposables = CompositeDisposable()
        }
        mDisposables!!.add(m!!)
    }

    fun clear() {
        if (mDisposables != null) { // 取消订阅
            mDisposables!!.dispose()
        }
    }

    companion object {
        /**
         * set HostnameVerifier
         * [HostnameVerifier]
         */
        protected fun getHostnameVerifier(hostUrls: Array<String>): HostnameVerifier {
            return HostnameVerifier { hostname, session ->
                var ret = false
                for (host in hostUrls) {
                    if (host.equals(hostname, ignoreCase = true)) {
                        ret = true
                    }
                }
                ret
            }
        }

        protected fun getSSLSocketFactory(
            context: Context?,
            certificates: IntArray
        ): SSLSocketFactory? {
            if (context == null) {
                throw NullPointerException("context == null")
            }
            val certificateFactory: CertificateFactory
            try {
                certificateFactory = CertificateFactory.getInstance("X.509")
                val keyStore =
                    KeyStore.getInstance(KeyStore.getDefaultType())
                keyStore.load(null, null)
                for (i in certificates.indices) {
                    val certificate =
                        context.resources.openRawResource(certificates[i])
                    keyStore.setCertificateEntry(
                        i.toString(),
                        certificateFactory.generateCertificate(certificate)
                    )
                    certificate?.close()
                }
                val sslContext =
                    SSLContext.getInstance("TLS")
                val trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
                trustManagerFactory.init(keyStore)
                sslContext.init(
                    null,
                    trustManagerFactory.trustManagers,
                    SecureRandom()
                )
                return sslContext.socketFactory
            } catch (e: Exception) {
            }
            return null
        }
    }
}