package com.gzinfo.kotlintiktok.util

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
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


    /**
     * 获取设备的可用内存大小
     *
     * @param context 应用上下文对象context
     * @return 当前内存大小
     */
    fun getDeviceUsableMemory(context: Context): Int {
        val am = context.getSystemService(
            Context.ACTIVITY_SERVICE) as ActivityManager
        val mi = ActivityManager.MemoryInfo()
        am.getMemoryInfo(mi)
        // 返回当前系统的可用内存
        return (mi.availMem / (1024 * 1024)).toInt()
    }


    fun getMobileModel(): String {
        var model: String? = Build.MODEL
        model = model?.trim { it <= ' ' } ?: ""
        return model
    }

    /**
     * 获取手机系统SDK版本
     *
     * @return 如API 17 则返回 17
     */
    val sdkVersion: Int
        get() = android.os.Build.VERSION.SDK_INT

}