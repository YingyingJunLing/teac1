package com.wd.tech.mvp.view.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.InfoCollectionBean
import com.wd.tech.mvp.presenter.presenterimpl.InfoCollectionPre
import com.wd.tech.mvp.view.base.BaseActivity
import com.wd.tech.mvp.view.contract.Contract

class MyCollectionActivity : BaseActivity<Contract.IInfoCollectionView,InfoCollectionPre>(),Contract.IInfoCollectionView {

    var infoCollectionPre : InfoCollectionPre = InfoCollectionPre(this)

    override fun initActivityView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_my_collection)
    }

    override fun createPresenter(): InfoCollectionPre? {
        return infoCollectionPre
    }

    override fun initData() {
        var sharedPreferences : SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)
        var userId = sharedPreferences.getString("userId", "0")
        var sessionId = sharedPreferences.getString("sessionId", "0")
    }

    override fun initView() {

    }

    override fun onInfoCollectionSuccess(infoCollectionBean: InfoCollectionBean) {

    }

    override fun onFail() {

    }
}
