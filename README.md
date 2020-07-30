# Kotlin-mvp-Rxjava-dagger2-Retrofit-EasyPermission
Kotlin 版 快速搭建 Kotlin + MVP + RxJava + Retrofit + EasyPermission 的框架，方便快速开发新项目、减少开发成本

## 前言

前段时间学习了 Kotlin 的一些语法，然后就写了这个项目熟悉一下 Android的官方语言，总体下来，感觉比较爽，相比 Java 而言源代码行数有所减少、方法数也有所减少。
所以就搭了一个mvp的框架，来与大家一起分享

Kotlin 团队为 Android 开发提供了一套超越标准语言功能的工具：

- [Kotlin Android 扩展](https://www.kotlincn.net/docs/tutorials/android-plugin.html)是一个编译器扩展， 可以让你摆脱代码中的 `findViewById()` 调用，并将其替换为合成的编译器生成的属性。
- [Anko](http://github.com/kotlin/anko) 是一个提供围绕 Android API 的 Kotlin 友好的包装器的库 ，以及一个可以用 Kotlin 代码替换布局 .xml 文件的 DSL。

项目框架使用了androidx的库

## kotlin协程
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.2.1"
## Retrofit + okhttp提供的请求日志拦截器  
    implementation 'com.squareup.retrofit2:converter-gson:2.4.0'
    implementation 'com.squareup.okhttp3:logging-interceptor:3.10.0'
    implementation 'com.squareup.retrofit2:adapter-rxjava2:2.5.0'
    implementation 'com.squareup.retrofit2:retrofit:2.5.0'
    implementation 'io.reactivex.rxjava2:rxandroid:2.1.1'
    
## dagger
    implementation 'com.google.dagger:dagger:2.11'
    kapt 'com.google.dagger:dagger-compiler:2.11'
## glide
    implementation 'com.github.bumptech.glide:glide:4.9.0'
    kapt 'com.github.bumptech.glide:compiler:4.9.0'

## https://github.com/googlesamples/easypermissions
    implementation 'pub.devrel:easypermissions:3.0.0'
