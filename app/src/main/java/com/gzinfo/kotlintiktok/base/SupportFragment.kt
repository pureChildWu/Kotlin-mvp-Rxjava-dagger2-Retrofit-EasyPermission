package com.gzinfo.kotlintiktok.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment

/**
 *@ClassName:SupportFragment
 *@Author:CreatBy wlh
 *@Time:2020/7/24 16点
 *@Email:m15904921255@163.com
 *@Desc:TODO
 */
open class SupportFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    fun showWaiteDialog() {
    }

    fun closeWaiteDialog() {
    }

    /**
     * 跳转到其他的页面
     */
    fun ToOtherActivity(clazz: Class<*>?) {
        val intent = Intent(getActivity(), clazz)
        startActivity(intent)
    }


}