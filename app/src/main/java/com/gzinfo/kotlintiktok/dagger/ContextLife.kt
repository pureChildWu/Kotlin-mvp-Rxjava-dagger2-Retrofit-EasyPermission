package com.gzinfo.kotlintiktok.dagger

import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import javax.inject.Qualifier

/**
 *@ClassName:ContextLife
 *@Author:CreatBy wlh
 *@Time:2020/7/24 15点
 *@Email:m15904921255@163.com
 *@Desc:TODO
 */
@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
annotation class ContextLife(val value: String = "Application")