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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = initFragmentView(inflater)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initFragmentChildView(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState != null) {
            initFragmentData(savedInstanceState)
        }
    }

    abstract fun initFragmentView(inflater : LayoutInflater): View

    abstract fun initFragmentChildView(view : View)

    abstract fun initFragmentData(savedInstanceState : Bundle)

    override fun onDestroy() {
        super.onDestroy()
        if (null != mPresenter) {
            mPresenter.detachView()
        }
    }
}