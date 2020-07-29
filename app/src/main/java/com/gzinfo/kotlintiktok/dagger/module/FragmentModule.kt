package com.gzinfo.kotlintiktok.dagger.module

import android.app.Activity
import androidx.fragment.app.Fragment
import com.gzinfo.kotlintiktok.dagger.FragmentScope
import dagger.Module
import dagger.Provides

/**
 *@ClassName:FragmentModule
 *@Author:CreatBy wlh
 *@Time:2020/7/24 15点
 *@Email:m15904921255@163.com
 *@Desc:TODO
 */
@Module
class FragmentModule(fragment: Fragment) {
    private val fragment: Fragment

    @Provides
    @FragmentScope
    fun provideActivity(): Activity {
        return fragment.activity!!
    }

    init {
        this.fragment = fragment
    }
}
