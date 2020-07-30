package com.gzinfo.kotlintiktok.http

import com.gzinfo.kotlintiktok.base.BaseView
import io.reactivex.subscribers.ResourceSubscriber

/**
 *@ClassName:CommonSubscirber
 *@Author:CreatBy wlh
 *@Time:2020/7/24 10点
 *@Email:m15904921255@163.com
 *@Desc:TODO
 */
public abstract class CommonSubscriber<T>(view: BaseView) : ResourceSubscriber<HttpResult<T>>() {
    private val mView: BaseView = view
    protected abstract fun onAnalysisNext(t: T)
    override fun onStart() {
        super.onStart()
        mView.showWaiteDialog()
    }

    override fun onNext(t: HttpResult<T>) {
        mView.closeWaiteDialog()
        val status = t.getCode()
        when (status) {
            "0" -> onAnalysisNext(t.getData())
            else -> mView.showMsg(t.getMessage()!!)
        }
    }

    override fun onError(t: Throwable) {
        mView.closeWaiteDialog()
        t.printStackTrace()
    }

    override fun onComplete() {
        mView.closeWaiteDialog()
    }

}
