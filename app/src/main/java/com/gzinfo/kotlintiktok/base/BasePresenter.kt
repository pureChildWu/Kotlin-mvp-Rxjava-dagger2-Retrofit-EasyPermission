package com.gzinfo.kotlintiktok.base

/**
 *@ClassName:BasePresenter
 *@Author:CreatBy wlh
 *@Time:2020/7/24 16点
 *@Email:m15904921255@163.com
 *@Desc:TODO
 */
interface BasePresenter<T : BaseView> {
     fun attachView(view: T)
     fun detachView()
}
