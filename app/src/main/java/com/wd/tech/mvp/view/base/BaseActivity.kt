package com.wd.tech.mvp.view.base

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.wd.tech.mvp.presenter.base.BasePresenter
import me.jessyan.autosize.internal.CustomAdapt

abstract class BaseActivity<V,T : BasePresenter<V>> : AppCompatActivity(), CustomAdapt {

    override fun isBaseOnWidth(): Boolean {
        return false
    }

    override fun getSizeInDp(): Float {
        return 667F
    }

    var basePresenter:T?=null
    var mContext: Context?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //加载布局
        initActivityView(savedInstanceState)
        mContext = this@BaseActivity
        //创建层
        basePresenter = createPresenter()
        //加载控件
        initView()
        //加载数据
        initData()
        //绑定p
        if (null !=basePresenter)
        {
            basePresenter!!.attachView(this as V)
        }

        //权限
        statePermission()
    }

    abstract fun createPresenter(): T?

    abstract fun initActivityView(savedInstanceState: Bundle?)

    abstract fun initData()

    abstract fun initView()

    private fun statePermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val mStatenetwork = arrayOf(
                //window权限
                Manifest.permission.SYSTEM_ALERT_WINDOW,
                //写的权限
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                //读的权限
                Manifest.permission.READ_EXTERNAL_STORAGE,
                //入网权限
                Manifest.permission.ACCESS_NETWORK_STATE,
                //WIFI权限
                Manifest.permission.ACCESS_WIFI_STATE,
                //读手机权限
                Manifest.permission.READ_PHONE_STATE,
                //网络权限
                Manifest.permission.INTERNET,
                //位置权限
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
                //相机
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_APN_SETTINGS,
                Manifest.permission.ACCESS_NETWORK_STATE
            )
            ActivityCompat.requestPermissions(this, mStatenetwork, 100)
        }
    }
}