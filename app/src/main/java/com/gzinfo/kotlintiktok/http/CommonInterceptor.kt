package com.gzinfo.kotlintiktok.http

import com.gzinfo.kotlintiktok.util.GsonUtil
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.RequestBody
import okhttp3.Response
import okio.Buffer
import java.io.IOException
import java.util.*

/**
 *@ClassName:CommonInterceptor
 *@Author:CreatBy wlh
 *@Time:2020/7/24 11点
 *@Email:m15904921255@163.com
 *@Desc:TODO
 */
class CommonInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val oldRequest = chain.request()
        val url = oldRequest.url()


        // 添加新的参数
        val authorizedUrlBuilder = oldRequest.url()
            .newBuilder()
            .scheme(oldRequest.url().scheme())
            .host(oldRequest.url().host())

            val builder = oldRequest.newBuilder()

        var newRequest = builder.method(oldRequest.method(), oldRequest.body())
            .url(authorizedUrlBuilder.build())
            .build()

        return chain.proceed(newRequest)
    }

    /***截取请求参数再转json */
    private fun getRequstParameter(url: HttpUrl): String? {
        val strings = url.queryParameterNames()
        if (strings.size == 0) {
            return ""
        }
        val map: MutableMap<String, String> =
            HashMap()
        for (name in strings) {
            val list = url.queryParameterValues(name)
            if (list != null && list.size != 0) {
                map[name] = list[0]
            }
        }
        return GsonUtil.getIns()!!.toJson(map)
    }

    companion object {
        private fun bodyToString(request: RequestBody?): String {
            return try {
                val buffer = Buffer()
                if (request != null) request.writeTo(buffer) else return ""
                buffer.readUtf8()
            } catch (e: IOException) {
                "did not work"
            }
        }
    }
}