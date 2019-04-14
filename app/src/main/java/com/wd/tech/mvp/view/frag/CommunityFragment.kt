package com.wd.tech.mvp.view.frag

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.CommunityListBean
import com.wd.tech.mvp.presenter.presenterimpl.CommunityListPresenter
import com.wd.tech.mvp.view.adapter.CommunityListAdapter
import com.wd.tech.mvp.view.base.BaseFragment
import com.wd.tech.mvp.view.contract.Contract

class CommunityFragment : BaseFragment<Contract.ICommunityListView,CommunityListPresenter>(),Contract.ICommunityListView {

    lateinit var adapter: CommunityListAdapter

    override fun initFragmentView(inflater: LayoutInflater): View {
        val view : View = View.inflate(context,R.layout.frag_community,null)
        return view
    }

    override fun initFragmentChildView(view: View) {

    }

    override fun initFragmentData(savedInstanceState: Bundle?) {
        val communityListPresenter : CommunityListPresenter = CommunityListPresenter(this)
        val hashMap : HashMap<String,Int> = HashMap()
        communityListPresenter.onICommunityListPre(hashMap,1,10)
    }

    override fun onSuccess(communityListBean: CommunityListBean) {
        val list = communityListBean.result
        Log.i("列表",list.get(0).content)
        /*adapter = CommunityListAdapter(this.requireContext(),list)
        xRecycle_community.adapter = adapter*/
    }

    override fun onFail() {

    }
}