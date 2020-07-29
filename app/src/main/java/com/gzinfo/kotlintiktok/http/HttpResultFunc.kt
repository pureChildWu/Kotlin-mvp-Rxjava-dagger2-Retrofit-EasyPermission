package com.gzinfo.kotlintiktok.http

import android.util.Log
import io.reactivex.functions.Function

/**
 *@ClassName:HttpResultFunc
 *@Author:CreatBy wlh
 *@Time:2020/7/24 10点
 *@Email:m15904921255@163.com
 *@Desc:TODO
 */

class HttpResultFunc<T> : Function<HttpResult<T?>?, T?> {

    override fun apply(t: HttpResult<T?>): T? {
        if (!t.getCode().equals("200")) {
            Log.e("code != 200" , "   返回信息" + t.getMessage())
            throw ApiException(t.getCode())
        }
        return t.getData()
    }
}
