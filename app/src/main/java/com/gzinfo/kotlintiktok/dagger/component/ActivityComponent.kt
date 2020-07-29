package com.gzinfo.kotlintiktok.dagger.component

import android.app.Activity
import com.gzinfo.kotlintiktok.MainActivity
import com.gzinfo.kotlintiktok.dagger.ActivityScope
import com.gzinfo.kotlintiktok.dagger.module.ActivityModule
import dagger.Component

/**
 *@ClassName:ActivityComponent
 *@Author:CreatBy wlh
 *@Time:2020/7/24 16点
 *@Email:m15904921255@163.com
 *@Desc:TODO
 */
@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [ActivityModule::class])
public interface ActivityComponent {
    fun getActivity(): Activity
    fun inject(mainActivity: MainActivity)
}