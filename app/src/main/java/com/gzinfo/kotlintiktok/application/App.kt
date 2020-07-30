package com.gzinfo.kotlintiktok.application

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import com.gzinfo.kotlintiktok.BuildConfig
import com.gzinfo.kotlintiktok.dagger.component.AppComponent
import com.gzinfo.kotlintiktok.dagger.component.DaggerAppComponent
import com.gzinfo.kotlintiktok.dagger.module.AppModule
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import kotlin.properties.Delegates

/**
 *@ClassName:App
 *@Author:CreatBy wlh
 *@Time:2020/7/24 14点
 *@Email:m15904921255@163.com
 *@Desc:TODO
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        //日志输入初始化
        initConfig()
    }


    /**
     * 初始化配置
     */
    private fun initConfig() {

        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false)  // 隐藏线程信息 默认：显示
            .methodCount(0)         // 决定打印多少行（每一行代表一个方法）默认：2
            .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
            .tag("kotlin_test")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
            .build()
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }

    companion object {

        private val TAG = "MyApplication"

        var instance: App by Delegates.notNull()
            private set

        val appComponent: AppComponent
            get() = DaggerAppComponent.builder()
                .appModule(AppModule(instance))
                .build()
    }
}
