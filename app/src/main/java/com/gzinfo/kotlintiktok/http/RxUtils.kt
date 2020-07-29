package com.gzinfo.kotlintiktok.http

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

/**
 *@ClassName:RxUtils
 *@Author:CreatBy wlh
 *@Time:2020/7/23 17点
 *@Email:m15904921255@163.com
 *@Desc:TODO
 */
object RxUtil {
    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
    </T> */
    fun <T> rxScheduler(): FlowableTransformer<T, T> {    //compose简化线程
        return FlowableTransformer { observable ->
            observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    /**
     * 统一返回结果处理
     *
     * @param <T>
     * @return
    </T> */
    fun <T> listResult(): FlowableTransformer<HttpResult<T>, T> {   //compose判断结果
        return FlowableTransformer { upstream ->
            upstream.flatMap(Function<HttpResult<T>, Flowable<T>> { tListResponse ->
                if (tListResponse.getCode().equals("200")) {
                    createData(tListResponse.getData())
                } else {
                    Flowable.error(
                        ApiException(
                            "服务器返回error",
                            tListResponse.getCode()
                        )
                    )
                }
            })
        }
    }

    /**
     * 统一返回结果处理
     *
     * @param <T>
     * @return
    </T> */
    fun <T> dataResult(): FlowableTransformer<HttpResult<T>, T> {   //compose判断结果
        return FlowableTransformer { upstream ->
            upstream.flatMap(Function<HttpResult<T>, Flowable<T>> { tDateResponse ->
                if (tDateResponse.getCode().equals("200")) {
                    createData(tDateResponse.getData())
                } else {
                    Flowable.error(
                        ApiException(
                            "服务器返回error",
                            tDateResponse.getCode()
                        )
                    )
                }
            })
        }
    }

    /**
     * 生成Flowable
     *
     * @param <T>
     * @return
    </T> */
    fun <T> createData(t: T?): Flowable<T> {
        return Flowable.create({ emitter ->
            try {
                t?.let { emitter.onNext(it) }
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }, BackpressureStrategy.BUFFER)
    }
}
