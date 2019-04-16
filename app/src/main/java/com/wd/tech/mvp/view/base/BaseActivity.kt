package com.wd.tech.mvp.view.base

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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
    }

    abstract fun createPresenter(): T?

    abstract fun initActivityView(savedInstanceState: Bundle?)

    abstract fun initData()

    abstract fun initView()

}