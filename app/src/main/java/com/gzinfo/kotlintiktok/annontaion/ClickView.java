package com.gzinfo.kotlintiktok.annontaion;

import androidx.annotation.IdRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName:ClickView
 * @Author:CreatBy wlh
 * @Time:2020/8/28 15点
 * @Email:m15904921255@163.com
 * @Desc:TODO
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ClickView {
    @IdRes int[] value();
}
