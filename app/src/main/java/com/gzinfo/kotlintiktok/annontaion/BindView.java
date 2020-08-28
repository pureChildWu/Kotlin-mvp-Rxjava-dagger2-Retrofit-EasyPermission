package com.gzinfo.kotlintiktok.annontaion;

import androidx.annotation.IdRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName:BindView
 * @Author:CreatBy wlh
 * @Time:2020/8/28 14点
 * @Email:m15904921255@163.com
 * @Desc:TODO
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BindView {
    @IdRes
    int value();
}
