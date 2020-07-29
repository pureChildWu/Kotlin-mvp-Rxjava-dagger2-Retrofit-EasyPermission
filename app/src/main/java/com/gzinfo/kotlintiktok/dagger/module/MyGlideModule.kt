package com.gzinfo.kotlintiktok.dagger.module

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions

/**
 *@ClassName:MyGlideModule
 *@Author:CreatBy wlh
 *@Time:2020/7/24 15点
 *@Email:m15904921255@163.com
 *@Desc:TODO
 */
@GlideModule
class MyGlideModule : AppGlideModule(){
    /**
     * 全局配置Glide选项
     */
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
        // 例如：全局设置图片格式为RGB_565
        builder.setDefaultRequestOptions(RequestOptions().format(DecodeFormat.PREFER_RGB_565))
    }

    /**
     * 注册自定义组件
     */
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
    }
}