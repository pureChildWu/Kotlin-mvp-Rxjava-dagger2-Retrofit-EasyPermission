package com.gzinfo.kotlintiktok.dagger.component

import android.app.Activity
import com.gzinfo.kotlintiktok.dagger.FragmentScope
import com.gzinfo.kotlintiktok.dagger.module.FragmentModule
import dagger.Component

/**
 *@ClassName:FragmentComponent
 *@Author:CreatBy wlh
 *@Time:2020/7/24 16点
 *@Email:m15904921255@163.com
 *@Desc:TODO
 */
@FragmentScope
@Component(dependencies = [AppComponent::class],modules = [FragmentModule::class])
public interface FragmentComponent {
    fun getActivity(): Activity?
}