package com.wd.tech.mvp.view.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wd.tech.mvp.presenter.base.BasePresenter

abstract class BaseFragment<V,T : BasePresenter<V>> : Fragment() {

    var mPresenter : BasePresenter<V> = BasePresenter()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
        initFragmentView(inflater)
        initFragmentChildView()
        initFragmentData()
    }

    abstract protected fun initFragmentView(inflater : LayoutInflater) : View

    abstract protected fun initFragmentChildView();

    abstract protected fun initFragmentData();

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}