package com.gzinfo.kotlintiktok.application

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import com.gzinfo.kotlintiktok.dagger.component.AppComponent
import com.gzinfo.kotlintiktok.dagger.component.DaggerAppComponent
import com.gzinfo.kotlintiktok.dagger.module.AppModule
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
