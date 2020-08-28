package com.gzinfo.kotlintiktok

import android.Manifest
import android.content.Intent
import android.view.View
import androidx.appcompat.widget.FitWindowsLinearLayout
import androidx.recyclerview.widget.ItemTouchHelper
import com.gzinfo.kotlintiktok.base.BaseActivity
import com.orhanobut.logger.Logger
import com.orhanobut.logger.Logger.e
import kotlinx.android.synthetic.main.activity_main.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import retrofit2.Retrofit
import java.util.*
import kotlin.random.Random


class MainActivity : BaseActivity<MainPresenter>() ,MainControl.View,View.OnClickListener {

    override fun initData() {
        mPresenter?.attachView(this)//mPresenter 初始化
        mPresenter?.let { lifecycle.addObserver(it) }

        checkPermission()
        text_view.setOnClickListener {
            mPresenter?.getAdd()
        }
        net_button.setOnClickListener(this@MainActivity)
        button1.setOnClickListener(this)
//        ItemTouchHelper
//        var pre = PriorityQueue();

        show(3){
            print("gaojie$it")
            it
        }
//        FitWindowsLinearLayout
//        Retrofit
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    override fun onClick(v: View?) {
        when(v?.id){
            net_button.id -> {
               getNetText()
            }
            button1.id -> {
                startActivity(Intent(this@MainActivity,MainActivity2().javaClass))
            }
        }

    }

    private fun getNetText() {//网络请求示例
        mPresenter?.getTestNet()
    }


    override fun initInject() {
        getActivityComponent().inject(this)
    }

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun add() {
        text_view.text = Random(3).nextInt().toString()
    }

    override fun rebackTestData(any: Any) {
        showMsg(any.toString())
        Logger.e(any.toString())
    }

    /**
     * 6.0以下版本(系统自动申请) 不会弹框
     * 有些厂商修改了6.0系统申请机制，他们修改成系统自动申请权限了
     */
    private fun checkPermission(){
        val perms = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        EasyPermissions.requestPermissions(this, "KotlinMvp应用需要以下权限，请允许", 0, *perms)
        Logger.d("***** checkPermission **** ")
    }


    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        if (requestCode == 0) {
            if (perms.isNotEmpty()) {
                if (perms.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    //申请的权限被允许时，做逻辑处理  拒绝时已经在base 基类里面做了处理
                }
            }
        }
    }


    /**
     * KOTLIN 高阶函数
     */
    fun show( i :Int, gaojie : (Int) -> Int){
        println(i)
        var gaojie1 = gaojie(i)
        println("return$gaojie1")
    }
}