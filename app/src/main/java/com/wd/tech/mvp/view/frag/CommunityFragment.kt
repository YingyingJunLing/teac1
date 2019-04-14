package com.wd.tech.mvp.view.frag

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.CommunityListBean
import com.wd.tech.mvp.model.bean.CommunityListBeanResult
import com.wd.tech.mvp.presenter.presenterimpl.CommunityListPresenter
import com.wd.tech.mvp.view.adapter.CommunityListAdapter
import com.wd.tech.mvp.view.base.BaseFragment
import com.wd.tech.mvp.view.contract.Contract
import kotlinx.android.synthetic.main.frag_community.*
import kotlinx.android.synthetic.main.frag_community.view.*

class CommunityFragment : BaseFragment<Contract.ICommunityListView,CommunityListPresenter>(),Contract.ICommunityListView {
    override fun initFragmentView(inflater: LayoutInflater): View {
        var view : View = View.inflate(context,R.layout.frag_community,null)
        return view
    }

    override fun initFragmentChildView(view: View) {

    }

    override fun initFragmentData(savedInstanceState: Bundle) {

    }

    override fun onSuccess(communityListBean: CommunityListBean) {

    }

    override fun onFail() {

    }
}