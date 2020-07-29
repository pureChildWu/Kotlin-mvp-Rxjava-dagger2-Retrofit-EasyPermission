package com.gzinfo.kotlintiktok.base

/**
 *@ClassName:BaseView
 *@Author:CreatBy wlh
 *@Time:2020/7/24 10点
 *@Email:m15904921255@163.com
 *@Desc:TODO
 */
interface BaseView {
    fun showMsg(msg: String)

    fun toLogin()

    fun showWaiteDialog()

    fun closeWaiteDialog()
}