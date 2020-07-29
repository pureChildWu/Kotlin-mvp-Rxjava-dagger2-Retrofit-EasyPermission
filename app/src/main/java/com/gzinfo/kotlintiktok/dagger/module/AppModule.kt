package com.gzinfo.kotlintiktok.dagger.module

import android.app.Activity
import com.gzinfo.kotlintiktok.application.App
import com.gzinfo.kotlintiktok.base.SupportActivity
import com.gzinfo.kotlintiktok.dagger.ContextLife
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 *@ClassName:AppModule
 *@Author:CreatBy wlh
 *@Time:2020/7/24 15点
 *@Email:m15904921255@163.com
 *@Desc:TODO
 */
@Module
public class AppModule {

    private var application: App? = null
    constructor(application: App?){
        this.application = application
    }

    @Provides
    @Singleton
    @ContextLife("Application")
    fun provideApplicationContext(): App? {
        return application
    }

    @Provides
    fun provideRetrofitHelper(): Activity {
        return SupportActivity()
    }
}