package com.gzinfo.kotlintiktok

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.gzinfo.kotlintiktok.MainControl.*
import com.gzinfo.kotlintiktok.base.BaseView
import com.gzinfo.kotlintiktok.base.RxPresenter
import com.gzinfo.kotlintiktok.http.CommonSubscriber
import com.gzinfo.kotlintiktok.http.RetrofitHelper
import com.orhanobut.logger.Logger
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

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)//present 同绑定的 activity生命周期
    fun onCreat(){
        Logger.e("OnLifecycleEvent -- > onCreat")

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestryoy(){
        Logger.e("OnLifecycleEvent -- > onDestroy")

    }

}
