package com.gzinfo.kotlintiktok.dagger.component

import com.gzinfo.kotlintiktok.application.App
import com.gzinfo.kotlintiktok.dagger.ContextLife
import com.gzinfo.kotlintiktok.dagger.module.AppModule
import dagger.Component
import javax.inject.Singleton

/**
 *@ClassName:AppComponent
 *@Author:CreatBy wlh
 *@Time:2020/7/24 15点
 *@Email:m15904921255@163.com
 *@Desc:TODO
 */
@Singleton
@Component(modules = [AppModule::class])
open interface AppComponent {
    @ContextLife("Application")
    fun getContext(): App? // 提供App的Context
    //    RetrofitHelper retrofitHelper();  //提供http的帮助类
}