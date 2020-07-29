package com.gzinfo.kotlintiktok.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import com.gzinfo.kotlintiktok.application.App

/**
 *@ClassName:CommontUtils
 *@Author:CreatBy wlh
 *@Time:2020/7/24 14点
 *@Email:m15904921255@163.com
 *@Desc:TODO
 */
object CommonUtils {

    /**
     * 检查是否有可用网络
     */
    @SuppressLint("MissingPermission")
    fun isNetworkConnected(): Boolean {
        val connectivityManager = App.instance!!.getApplicationContext()
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null
    }

}