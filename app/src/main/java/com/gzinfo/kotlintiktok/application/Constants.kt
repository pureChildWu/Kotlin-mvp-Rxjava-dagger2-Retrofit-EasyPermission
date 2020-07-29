package com.gzinfo.kotlintiktok.application

import java.io.File

/**
 *@ClassName:Constants
 *@Author:CreatBy wlh
 *@Time:2020/7/24 14点
 *@Email:m15904921255@163.com
 *@Desc:TODO
 */
public class Constants {
    companion object{
        private var PATH_DATA: String = App.instance!!.cacheDir.absolutePath + File.separator + "data"
        var PATH_CACHE: String = "$PATH_DATA/NetCache"
    }

}