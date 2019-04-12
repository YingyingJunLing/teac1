package com.wd.tech.mvp.view.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wd.tech.mvp.presenter.base.BasePresenter
import me.jessyan.autosize.internal.CustomAdapt

abstract class BaseFragment<V,T : BasePresenter<V>> : Fragment(), CustomAdapt {

    override fun isBaseOnWidth(): Boolean {
        return false
    }

    override fun getSizeInDp(): Float {
        return 1080F
    }

    var mPresenter : BasePresenter<V> = BasePresenter()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initFragmentData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
        initFragmentView(inflater)
        initFragmentChildView()
    }

    abstract protected fun initFragmentView(inflater : LayoutInflater) : View

    abstract protected fun initFragmentChildView();

    abstract protected fun initFragmentData();

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}