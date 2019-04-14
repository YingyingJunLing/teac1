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
        return 667F
    }
    var mPresenter : BasePresenter<V> = BasePresenter()

    //加载视图
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view : View = initFragmentView(inflater)
        initFragmentData(savedInstanceState)
        return view
    }

    //加载子控件
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initFragmentChildView(view)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initFragmentData(savedInstanceState)
    }


    abstract protected fun initFragmentView(inflater : LayoutInflater) : View

    abstract protected fun initFragmentChildView(view : View);

    abstract protected fun initFragmentData(savedInstanceState: Bundle?);

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}