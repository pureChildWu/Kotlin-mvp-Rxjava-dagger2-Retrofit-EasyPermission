package com.gzinfo.kotlintiktok

import com.gzinfo.kotlintiktok.base.BasePresenter
import com.gzinfo.kotlintiktok.base.BaseView

/**
 *@ClassName:MainControl
 *@Author:CreatBy wlh
 *@Time:2020/7/24 17点
 *@Email:m15904921255@163.com
 *@Desc:TODO
 */
interface MainControl {
    interface View : BaseView{
        fun add();
    }
    interface Presenter : BasePresenter<View>{
        fun getAdd();
    }
}