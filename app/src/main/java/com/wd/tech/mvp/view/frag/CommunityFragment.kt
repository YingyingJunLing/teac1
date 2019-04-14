package com.wd.tech.mvp.view.frag

import android.os.Bundle
import android.view.View
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.CommunityListBean
import com.wd.tech.mvp.presenter.presenterimpl.CommunityListPresenter
import com.wd.tech.mvp.view.base.BaseFragment
import com.wd.tech.mvp.view.contract.Contract

class CommunityFragment : BaseFragment<Contract.ICommunityListView,CommunityListPresenter>(),Contract.ICommunityListView {
    override fun setView(): Int {
        return R.layout.frag_community
    }

    override fun initFragmentData(savedInstanceState: Bundle?) {

    }

    override fun initFragmentChildView(view: View) {

    }

    override fun createPresenter(): CommunityListPresenter? {
        var communityListPresenter : CommunityListPresenter = CommunityListPresenter(this)
        return communityListPresenter
    }

    override fun onSuccess(communityListBean: CommunityListBean) {

    }

    override fun onFail() {

    }

}