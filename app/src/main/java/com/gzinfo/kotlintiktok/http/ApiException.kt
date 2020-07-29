package com.gzinfo.kotlintiktok.http

/**
 *@ClassName:ApiException
 *@Author:CreatBy wlh
 *@Time:2020/7/23 17点
 *@Email:m15904921255@163.com
 *@Desc:TODO
 */
internal class ApiException : Exception {
    private val code: String? = null

    fun getCode(): String? {
        return code
    }

    constructor(code: String?) {}
    constructor(msg: String?, code: String?) {}
}