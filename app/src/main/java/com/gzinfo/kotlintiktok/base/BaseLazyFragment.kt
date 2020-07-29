package com.gzinfo.kotlintiktok.base

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.Nullable
import com.gzinfo.kotlintiktok.application.App
import com.gzinfo.kotlintiktok.dagger.component.DaggerFragmentComponent
import com.gzinfo.kotlintiktok.dagger.component.FragmentComponent
import com.gzinfo.kotlintiktok.dagger.module.FragmentModule
import com.gzinfo.kotlintiktok.util.ToastUtils
import io.reactivex.annotations.NonNull
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject

/**
 *@ClassName:BaseLazyFragment
 *@Author:CreatBy wlh
 *@Time:2020/7/24 16点
 *@Email:m15904921255@163.com
 *@Desc:TODO
 */
abstract class BaseLazyFragment<T : RxPresenter<*>?> : SupportFragment(), BaseView , EasyPermissions.PermissionCallbacks{
    protected var mRootView: View? = null
    protected var mActivity: Activity? = null
    private var isV = false
    private var isPrepared = false
    private var isFirst = true
    @set:Inject
    var mPresenter: T? = null
    protected fun getFragmentComponent(): FragmentComponent {
        return DaggerFragmentComponent.builder()
            .appComponent(App.appComponent)
            .fragmentModule(getFragmentModule())
            .build()
    }

    protected fun getFragmentModule(): FragmentModule {
        return FragmentModule(this)
    }

    //--------------------system method callback------------------------//
    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isPrepared = true
        mActivity = activity
    }

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayoutId(), container, false)
        }
        initInject()
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        mPresenter?.attachView(this)
        super.onViewCreated(view, savedInstanceState)
    }

    /**
     * 隐藏软键盘
     */
    fun hintKeyBoard() {
        //拿到InputMethodManager
        val imm =
            activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        //如果window上view获取焦点 && view不为空
        if (imm.isActive && activity!!.currentFocus != null) {
            //拿到view的token 不为空
            if (activity!!.currentFocus!!.windowToken != null) {
                //表示软键盘窗口总是隐藏，除非开始时以SHOW_FORCED显示。
                imm.hideSoftInputFromWindow(
                    activity!!.currentFocus!!.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint) {
            isV = true
            lazyLoad()
        } else {
            isV = false
            onInvisible()
        }
    }

    override fun toLogin() {

    }

    override fun onResume() {
        super.onResume()
        if (userVisibleHint) {
            userVisibleHint = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter?.detachView()
    }
    //--------------------------------method---------------------------//
    /**
     * 懒加载
     */
    protected fun lazyLoad() {
        if (!isPrepared || !isVisible || !isFirst) {
            return
        }
        initLazyData()
        isFirst = false
    }

    override fun showMsg(msg: String) {
        ToastUtils.show(msg)
    }

    //--------------------------abstract method------------------------//
    abstract fun initInject()

    /**
     * fragment被设置为不可见时调用
     */
    abstract fun onInvisible()

    /**
     * 这里获取数据，刷新界面
     */
    abstract fun initLazyData()

    /**
     * 初始化布局，请不要把耗时操作放在这个方法里，这个方法用来提供一个
     * 基本的布局而非一个完整的布局，以免ViewPager预加载消耗大量的资源。
     */
    abstract fun getLayoutId(): Int

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
            Toast.makeText(activity, "已拒绝权限" + sb + "并不再询问", Toast.LENGTH_SHORT).show()
            AppSettingsDialog.Builder(this)
                .setRationale("此功能需要" + sb + "权限，否则无法正常使用，是否打开设置")
                .setPositiveButton("好")
                .setNegativeButton("不行")
                .build()
                .show()
        }
    }
}
