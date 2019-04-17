package com.wd.tech.wxapi

import android.os.Bundle
import com.wd.tech.mvp.presenter.presenterimpl.WechatLoginPresenter
import com.wd.tech.mvp.view.base.BaseActivity
import com.wd.tech.mvp.view.contract.Contract

class WXEntryActivity : BaseActivity<Contract.IWechatLoginView, WechatLoginPresenter>(),Contract.IWechatLoginView {
    var wechatLoginPresenter:WechatLoginPresenter?=null
    override fun createPresenter(): WechatLoginPresenter? {
        wechatLoginPresenter= WechatLoginPresenter()
        return wechatLoginPresenter
    }

    override fun initActivityView(savedInstanceState: Bundle?) {

    }

    override fun initData() {
    }

    override fun initView() {

    }

    override fun onIWechatLoginSuccess(any: Any) {

    }

    override fun onIWechatLoginFail() {

    }
}