package com.gzinfo.kotlintiktok

import com.gzinfo.kotlintiktok.MainControl.*
import com.gzinfo.kotlintiktok.base.RxPresenter
import com.gzinfo.kotlintiktok.http.CommonSubscriber
import com.gzinfo.kotlintiktok.http.RetrofitHelper
import javax.inject.Inject

/**
 *@ClassName:MainPresenter
 *@Author:CreatBy wlh
 *@Time:2020/7/24 17点
 *@Email:m15904921255@163.com
 *@Desc:TODO
 */
class MainPresenter @Inject constructor() : RxPresenter<View>() , Presenter{

    override fun getAdd() {
        mView.add()
    }

    override fun getTestNet() {
        add(RetrofitHelper.ins?.getHomeInfo("667","123456",object : CommonSubscriber<Any>(mView){
            override fun onAnalysisNext(t: Any) {
                mView.rebackTestData(t)
            }
        }))
    }

}
