package com.gzinfo.kotlintiktok.util

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.*
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat

/**
 *@ClassName:AndroidStatusBarUtils
 *@Author:CreatBy wlh
 *@Time:2020/7/24 16点
 *@Email:m15904921255@163.com
 *@Desc:TODO
 */
class  AndroidStatusBarUtils {
   companion object{
       /**
        * @param
        * @return
        * @description 用于修改状态栏
        * @author 张岩
        * @time 2019/3/12 9:27
        */
       @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
       fun setStatusBarColor2(activity: Activity, statusColor: Int) {
           val window = activity.window
           //取消状态栏透明
           window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
           //添加Flag把状态栏设为可绘制模式
           window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
           //设置状态栏颜色
           window.statusBarColor = statusColor
           //设置系统状态栏处于可见状态
           window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
           //让view不根据系统窗口来调整自己的布局
           val mContentView =
               window.findViewById<View>(Window.ID_ANDROID_CONTENT) as ViewGroup
           val mChildView = mContentView.getChildAt(0)
           if (mChildView != null) {
               ViewCompat.setFitsSystemWindows(mChildView, false)
               ViewCompat.requestApplyInsets(mChildView)
           }
       }

       /**
        * @param
        * @return
        * @description 老版本获取状态栏高度
        * @author 张岩
        * @time 2019/3/12 9:27
        */
       fun getStatusBarHeight(context: Context): Int {
           var statusBarHeight = 0
           val res = context.resources
           val resourceId = res.getIdentifier("status_bar_height", "dimen", "android")
           if (resourceId > 0) {
               statusBarHeight = res.getDimensionPixelSize(resourceId)
           }
           return statusBarHeight
       }

       /**
        * 通过设置全屏，设置状态栏透明
        */
       fun fullScreen(activity: Activity, ColorRes: Int) {
           activity.window.decorView.systemUiVisibility =
               View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
           if (Build.VERSION.SDK_INT <= 20) {
               //5.0.1以下
               val window = activity.window
               window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
               val decorViewGroup = window.decorView as ViewGroup
               val statusBarView = View(window.context)
               val statusBarHeight = getStatusBarHeight(activity)
               val params =
                   FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, statusBarHeight)
               params.gravity = Gravity.TOP
               statusBarView.layoutParams = params
               statusBarView.setBackgroundColor(activity.resources.getColor(ColorRes))
               decorViewGroup.addView(statusBarView)
               val mContentView =
                   window.findViewById<View>(Window.ID_ANDROID_CONTENT) as ViewGroup
               val mChildView = mContentView.getChildAt(0)
               if (mChildView != null) {
                   //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 预留出系统 View 的空间.
                   mChildView.fitsSystemWindows = true
               }
           } else {
               val window = activity.window
               //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
               window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
               //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
               window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
               //设置状态栏颜色
               window.statusBarColor = activity.resources.getColor(ColorRes)
           }
       }
   }
}
