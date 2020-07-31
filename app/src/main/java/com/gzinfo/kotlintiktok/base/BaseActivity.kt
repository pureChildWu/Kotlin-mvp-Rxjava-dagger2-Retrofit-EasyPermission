package com.gzinfo.kotlintiktok.base

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import com.gzinfo.kotlintiktok.R
import com.gzinfo.kotlintiktok.application.App
import com.gzinfo.kotlintiktok.dagger.component.ActivityComponent
import com.gzinfo.kotlintiktok.dagger.component.DaggerActivityComponent
import com.gzinfo.kotlintiktok.dagger.module.ActivityModule
import com.gzinfo.kotlintiktok.util.AndroidStatusBarUtils
import com.gzinfo.kotlintiktok.util.ToastUtils
import io.reactivex.annotations.NonNull
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.io.File
import javax.inject.Inject

/**
 *@ClassName:BaseActivity
 *@Author:CreatBy wlh
 *@Time:2020/7/24 16点
 *@Email:m15904921255@163.com
 *@Desc:TODO
 */
abstract class BaseActivity< T : BasePresenter<*>> : SupportActivity(), BaseView, EasyPermissions.PermissionCallbacks {

    @set:Inject
    var mPresenter: T? = null

    //图片相关
    private var mExitTime: Long = 0
    private var isExit = false

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        fullScreen(R.color.white)
//        mPresenter?.attachView(this)//mPresenter 初始化
        initInject()
        initData()
    }

    /**
     * 通过设置全屏，设置状态栏透明
     */
    @RequiresApi(Build.VERSION_CODES.M)
    protected fun fullScreen(ColorRes: Int) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        val window = window
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        //设置状态栏颜色
        window.statusBarColor = resources.getColor(ColorRes)
    }


    abstract fun initData()

    abstract fun initInject()

    override fun showMsg(msg: String) {
        ToastUtils.show(msg)
    }

    override fun toLogin() {

    }

    /**
     * 隐藏软键盘
     */
    fun hintKeyBoard() { //拿到InputMethodManager
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        //如果window上view获取焦点 && view不为空
        if (imm.isActive && currentFocus != null) {
            //拿到view的token 不为空
            if (currentFocus!!.windowToken != null) {
                //表示软键盘窗口总是隐藏，除非开始时以SHOW_FORCED显示。
                imm.hideSoftInputFromWindow(
                    currentFocus!!.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
    }

    abstract fun getLayout(): Int

    protected fun getActivityComponent(): ActivityComponent {
        return DaggerActivityComponent.builder()
            .appComponent(App.appComponent)
            .activityModule(getActivityModule())
            .build()
    }

    protected fun getActivityModule(): ActivityModule {
        return ActivityModule(this)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isExit) {
                if (System.currentTimeMillis() - mExitTime > 2000) { //再按一次退出程序
                    mExitTime = System.currentTimeMillis()
                } else {
                    finish()
                }

                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    fun setExit(isExit: Boolean) {
        this.isExit = isExit
    }


    companion object {

        fun setAndroidNativeLightStatusBar(activity: Activity, dark: Boolean) {
            val decor = activity.window.decorView
            if (dark) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    decor.systemUiVisibility =
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
            } else {
                decor.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            }
        }
    }


    /**
     * 重写要申请权限的Activity或者Fragment的onRequestPermissionsResult()方法，
     * 在里面调用EasyPermissions.onRequestPermissionsResult()，实现回调。
     *
     * @param requestCode  权限请求的识别码
     * @param permissions  申请的权限
     * @param grantResults 授权结果
     */
    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    /**
     * 当权限被成功申请的时候执行回调
     *
     * @param requestCode 权限请求的识别码
     * @param perms       申请的权限的名字
     */
    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Log.i("EasyPermissions", "获取成功的权限$perms")
    }

    /**
     * 当权限申请失败的时候执行的回调
     *
     * @param requestCode 权限请求的识别码
     * @param perms       申请的权限的名字
     */
    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        //处理权限名字字符串
        val sb = StringBuffer()
        for (str in perms) {
            sb.append(str)
            sb.append("\n")
        }
        sb.replace(sb.length - 2, sb.length, "")
        //用户点击拒绝并不在询问时候调用
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            Toast.makeText(this, "已拒绝权限" + sb + "并不再询问", Toast.LENGTH_SHORT).show()
            AppSettingsDialog.Builder(this)
                .setRationale("此功能需要" + sb + "权限，否则无法正常使用，是否打开设置")
                .setPositiveButton("好")
                .setNegativeButton("不行")
                .build()
                .show()
        }
    }
}

