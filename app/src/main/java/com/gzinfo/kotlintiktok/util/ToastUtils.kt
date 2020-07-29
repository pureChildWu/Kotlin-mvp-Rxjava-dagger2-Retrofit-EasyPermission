package com.gzinfo.kotlintiktok.util

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.gzinfo.kotlintiktok.application.App

/**
 *@ClassName:ToastUtils
 *@Author:CreatBy wlh
 *@Time:2020/7/24 16点
 *@Email:m15904921255@163.com
 *@Desc:TODO
 */
object ToastUtils {

    fun show(msg: String) {
        Toast.makeText(App.instance,msg,Toast.LENGTH_SHORT).show()
    }

    fun showShort(view: View, msg: String) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show()
    }

    fun showLong(view: View, msg: String) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show()
    }


}