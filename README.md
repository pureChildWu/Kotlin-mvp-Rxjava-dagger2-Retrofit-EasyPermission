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

## easypermissions 为Android6.0 动态权限请求框架 
    ## https://github.com/googlesamples/easypermissions
    implementation 'pub.devrel:easypermissions:3.0.0'
    
    
如果要采用Mvp的写法，activity 需要继承 项目中的BaseActivity来完成，需要一个activity的control类，即MVP中的Model 类，view 则为activity present 需要继承RxPresenter来完成
如果是Fragment 则写法和 activity的 写法一样
如果要采用正常的mvc写法，只需要继承SimpleActivty或者SimpleFragment来完成，网络框架已经在BaseView中有了体现，使用时即使用Retrofit.getIns. ..... 就可以


## 网络请求示例
  - 1.首先在LCAPI 里面去写Retroit的调用api
  - 2.然后再RetrofitHelper里面去调用
  - 3.在present 里面就可以使用了
  - 具体调用的网络请求示例可查看MianActivity里面的示例
  
## 此开源项目仅做学习交流使用, 不可用于任何商业用途，如果你觉得不错, 对你有帮助, 欢迎点个 star 谢谢

