package com.gzinfo.kotlintiktok.dagger.module

import android.app.Activity
import com.gzinfo.kotlintiktok.dagger.ActivityScope
import dagger.Module
import dagger.Provides

/**
 *@ClassName:ActivityModule
 *@Author:CreatBy wlh
 *@Time:2020/7/24 15点
 *@Email:m15904921255@163.com
 *@Desc:TODO
 */
@Module
class ActivityModule(activity: Activity) {
    private val mActivity: Activity

    @Provides
    @ActivityScope
    fun provideActivity(): Activity {
        return mActivity
    }

    init {
        mActivity = activity
    }
}
