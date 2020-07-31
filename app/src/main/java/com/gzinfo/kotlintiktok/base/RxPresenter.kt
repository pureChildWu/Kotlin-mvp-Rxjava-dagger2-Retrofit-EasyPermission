package com.gzinfo.kotlintiktok.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.orhanobut.logger.Logger
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 *@ClassName:RxPresenter
 *@Author:CreatBy wlh
 *@Time:2020/7/24 16点
 *@Email:m15904921255@163.com
 *@Desc:TODO
 */
open class RxPresenter<T : BaseView> : BasePresenter<T> , LifecycleObserver {

    lateinit var mView: T
    private var mDisposables: CompositeDisposable? = null

    override fun detachView() {
        unSubscribe()
//        mView =  null;
    }

    private fun unSubscribe() {
        if (mDisposables != null) {
            mDisposables!!.dispose()
            mView.closeWaiteDialog()
        }
    }

    fun add(disposable: Disposable?) {
        if (mDisposables == null) {
            mDisposables = CompositeDisposable()
        }
        mDisposables!!.add(disposable!!)
    }

    override fun attachView(view: T) {
        mView = view
    }

}
