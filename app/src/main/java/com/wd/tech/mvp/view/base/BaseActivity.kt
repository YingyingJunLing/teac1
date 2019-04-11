package com.wd.tech.mvp.view.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.wd.tech.mvp.presenter.base.BasePresenter

abstract class BaseActivity<V,T : BasePresenter<V>> : AppCompatActivity() {

    var mPresenter : BasePresenter<V> = BasePresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivity(savedInstanceState)
        cratePresenter()
        getData()
    }

    abstract fun initActivity(savedInstanceState: Bundle?)

    abstract fun getData()

    abstract fun cratePresenter()

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}