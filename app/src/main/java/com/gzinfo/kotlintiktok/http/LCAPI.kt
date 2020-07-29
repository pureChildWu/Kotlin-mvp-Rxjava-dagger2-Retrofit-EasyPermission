package com.gzinfo.kotlintiktok.http

import io.reactivex.Flowable
import retrofit2.http.Body
import retrofit2.http.POST

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
    @POST("/api/systembase/getProvinceList")
    fun getHomeInfo(@Body jsonObject: String): Flowable<HttpResult<List<Any>>>

}