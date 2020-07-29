package com.gzinfo.kotlintiktok.http

import android.util.Log
import androidx.annotation.NonNull
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import java.io.IOException
import java.nio.charset.Charset
import java.util.*

/**
 *@ClassName:LoggingInterceptor
 *@Author:CreatBy wlh
 *@Time:2020/7/24 11点
 *@Email:m15904921255@163.com
 *@Desc:TODO
 */
class LoggingInterceptor : Interceptor {
    private val UTF8 = Charset.forName("UTF-8")

    override fun intercept(@NonNull chain: Interceptor.Chain): Response {
        //这个chain里面包含了request和response，
        val request = chain.request()
        val requestBody = request.body()
        var body: String? = null
        if (requestBody != null) {
            val buffer = Buffer()
            requestBody.writeTo(buffer)
            var charset = UTF8
            val contentType = requestBody.contentType()
            if (contentType != null) {
                charset = contentType.charset(UTF8)
            }
            if (charset != null) {
                body = buffer.readString(charset)
            }
        }
        val t1 = System.nanoTime() //请求发起的时间
        Log.e(this@LoggingInterceptor.javaClass.simpleName,
            String.format(
                Locale.CHINA, "发送请求\nmethod：%s\nurl：%s\nheaders: %sbody：%s",
                request.url(), chain.connection(), request.headers(), body
            )
        )
        val response = chain.proceed(request)
        val t2 = System.nanoTime() //收到响应的时间
        val responseBody = response.peekBody(1024 * 1024.toLong())

        //这里不能直接使用response.body().string()的方式输出日志
        //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
        //个新的response给应用层处理
        Log.e(this@LoggingInterceptor.javaClass.simpleName,
            String.format(
                Locale.CHINA, "接收响应: [%s] %n返回json:【%s】 %.1fms%n%s",
                response.request().url(),
                responseBody.string(),
                (t2 - t1) / 1e6,
                response.headers()
            )
        )
        return response
    }
}
