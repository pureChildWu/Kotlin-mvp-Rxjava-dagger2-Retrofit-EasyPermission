package com.gzinfo.kotlintiktok.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity

/**
 *@ClassName:SupportActivity
 *@Author:CreatBy wlh
 *@Time:2020/7/24 15点
 *@Email:m15904921255@163.com
 *@Desc:TODO
 */
open class SupportActivity : AppCompatActivity() {

     override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    /**
     * 只提示传入false
     *
     * @param titile
     * @param clazz
     */
    fun dialogShow(titile: String?, clazz: Class<*>?, activity: Activity?, falg: Boolean) {
        if (falg) {
            dialogShow(titile, clazz, activity)
            return
        }
        if (activity == null) {
            return
        }
    }

    fun dialogShow(titile: String?, clazz: Class<*>?, activity: Activity?) {

    }

    /**
     * 跳转到其他的页面
     */
    fun ToOtherActivity(clazz: Class<*>) {
        val intent = Intent(this, clazz)
        startActivity(intent)
    }

    fun showWaiteDialog() {

    }

    fun closeWaiteDialog() {

    }


}