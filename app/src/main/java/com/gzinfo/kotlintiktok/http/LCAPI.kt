package com.gzinfo.kotlintiktok.http

import com.google.gson.JsonObject
import io.reactivex.Flowable
import retrofit2.http.POST
import retrofit2.http.Query

/**
 *@ClassName:LCAPI
 *@Author:CreatBy wlh
 *@Time:2020/7/24 11点
 *@Email:m15904921255@163.com
 *@Desc:TODO
 */
interface LCAPI {

    /**
     * 测试
     */
    @POST("/jujia/utilController/login5.do")
    open fun getHomeInfo(@Query("name") name: String, @Query("psw") password: String): Flowable<HttpResult<Any>>

}