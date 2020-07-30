package com.gzinfo.kotlintiktok.http

import com.gzinfo.kotlintiktok.BuildConfig.DEBUG
import com.gzinfo.kotlintiktok.application.Constants
import com.gzinfo.kotlintiktok.util.CommonUtils
import com.gzinfo.kotlintiktok.util.HttpsUtils
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.InputStream
import java.util.concurrent.TimeUnit
import javax.net.ssl.X509TrustManager

/**
 *@ClassName:RetrofitHelper
 *@Author:CreatBy wlh
 *@Time:2020/7/24 13点
 *@Email:m15904921255@163.com
 *@Desc:TODO
 */
class RetrofitHelper//debug模式开启log
//设置超时
//错误重连
// 无网络时，设置超时为4周// 有网络时, 不缓存, 最大保存时长为0
private constructor() {
    private var api: LCAPI
    private val mOkHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()

    init {
        val pathCache: String = Constants.PATH_CACHE
        val cacheFile = File(pathCache)
        val cache = Cache(cacheFile, 1024 * 1024 * 50)
        val cacheInterceptor =
            Interceptor { chain: Interceptor.Chain ->
                var request = chain.request()
                if (!CommonUtils.isNetworkConnected()) {
                    request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build()
                }
                val response = chain.proceed(request)
                if (CommonUtils.isNetworkConnected()) {
                    val maxAge = 60
                    // 有网络时, 不缓存, 最大保存时长为0
                    response.newBuilder()
                        .header("Cache-Control", "public, max-age=$maxAge")
                        .addHeader("Content-Type", "application/json, text/json")
                        .addHeader("Content-Type", "multipart/form-data")
                        .build()
                } else {
                    // 无网络时，设置超时为4周
                    val maxStale = 60 * 60 * 24 * 28
                    response.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                        .build()
                }
                response
            }
        mOkHttpClientBuilder.addNetworkInterceptor(cacheInterceptor)
        mOkHttpClientBuilder.addInterceptor(cacheInterceptor)
        mOkHttpClientBuilder.addInterceptor(CommonInterceptor())
        if (DEBUG) { //debug模式开启log
            mOkHttpClientBuilder.addInterceptor(LoggingInterceptor())
        }
        mOkHttpClientBuilder.cache(cache)
        mOkHttpClientBuilder.connectTimeout(10, TimeUnit.SECONDS)
        mOkHttpClientBuilder.readTimeout(8, TimeUnit.SECONDS)
        mOkHttpClientBuilder.writeTimeout(8, TimeUnit.SECONDS)
        mOkHttpClientBuilder.retryOnConnectionFailure(true)
        val retrofit = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .client(mOkHttpClientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        api = retrofit.create(LCAPI::class.java)
    }


    /**
     * 首页
     */
    fun getHomeInfo(name: String,psd : String, subscriber: CommonSubscriber<Any>): CommonSubscriber<Any> {
        return toSubscribe(api.getHomeInfo(name,psd), subscriber)
    }



    /** */
    private fun <T> toSubscribe(o: Flowable<HttpResult<T>>, s: CommonSubscriber<T>): CommonSubscriber<T> {
        return o.subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(s)
    }

    /**
     * https单向认证
     * 用含有服务端公钥的证书校验服务端证书
     */
    fun setCertificates(vararg certificates: InputStream): RetrofitHelper {
        setCertificates(null, null, *certificates)
        return this
    }

    /**
     * https单向认证
     * 可以额外配置信任服务端的证书策略，否则默认是按CA证书去验证的，若不是CA可信任的证书，则无法通过验证
     */
    fun setCertificates(trustManager: X509TrustManager): RetrofitHelper {
        setCertificates(null, null, trustManager)
        return this
    }

    /**
     * https双向认证
     * bksFile 和 password -> 客户端使用bks证书校验服务端证书
     * certificates -> 用含有服务端公钥的证书校验服务端证书
     */
    fun setCertificates(bksFile: InputStream?, password: String?, vararg certificates: InputStream): RetrofitHelper {
        val sslParams: HttpsUtils.SSLParams = HttpsUtils.getSslSocketFactory(null, bksFile!!, password!!, certificates)
        mOkHttpClientBuilder.sslSocketFactory(sslParams.sSLSocketFactory!!, sslParams.trustManager!!)
        return this
    }

    /**
     * https双向认证
     * bksFile 和 password -> 客户端使用bks证书校验服务端证书
     * X509TrustManager -> 如果需要自己校验，那么可以自己实现相关校验，如果不需要自己校验，那么传null即可
     */
    fun setCertificates(bksFile: InputStream?, password: String?, trustManager: X509TrustManager): RetrofitHelper {
        val sslParams: HttpsUtils.SSLParams = HttpsUtils.getSslSocketFactory(trustManager, bksFile!!, password!!, null)
        mOkHttpClientBuilder.sslSocketFactory(sslParams.sSLSocketFactory!!, sslParams.trustManager!!)
        return this
    }

    companion object {
        var API_BASE_URL: String = "http://www.jjkj2017.com"
        private var INSTANCE: RetrofitHelper? = null

        @get:Synchronized
        val ins: RetrofitHelper?
            get() {
                if (INSTANCE == null) {
                    INSTANCE = RetrofitHelper()
                }
                return INSTANCE
            }

        /**
         * For Dev setting
         *
         * @return
         */
        @Synchronized
        fun rebuildInstance(): RetrofitHelper? {
            INSTANCE = null
            return ins
        }
    }
}