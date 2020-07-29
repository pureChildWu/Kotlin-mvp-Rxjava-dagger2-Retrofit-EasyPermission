package com.gzinfo.kotlintiktok.util

import com.google.gson.Gson

/**
 *@ClassName:GsonUtil
 *@Author:CreatBy wlh
 *@Time:2020/7/24 11点
 *@Email:m15904921255@163.com
 *@Desc:TODO
 */
class GsonUtil private constructor() {

    companion object{
        private var mgson: Gson? = null
        fun getIns(): Gson? {
            if (null == mgson) {
                mgson = Gson()
            }
            return mgson
        }
    }

}