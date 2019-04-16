package com.wd.tech.mvp.view.frag

import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.CommunityListBean
import com.wd.tech.mvp.presenter.presenterimpl.CommunityListPresenter
import com.wd.tech.mvp.view.adapter.CommunityListAdapter
import com.wd.tech.mvp.view.base.BaseFragment
import com.wd.tech.mvp.view.contract.Contract
import kotlinx.android.synthetic.main.frag_community.view.*
import java.util.*
import kotlin.collections.HashMap

class CommunityFragment : BaseFragment<Contract.ICommunityListView,CommunityListPresenter>(),Contract.ICommunityListView {

    lateinit var communityListPresenter : CommunityListPresenter
    lateinit var adapter : CommunityListAdapter
    var hashMap : HashMap<String,String> = HashMap()
    var xRecycle_community : XRecyclerView ?= null
    var page : Int = Random().nextInt(5)+1

    override fun setView(): Int {
        return R.layout.frag_community
    }

    override fun initFragmentData(savedInstanceState: Bundle?) {
        communityListPresenter.onICommunityListPre(hashMap,page,10)
    }

    override fun initFragmentChildView(view: View) {
        view.xRecycle_community.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        xRecycle_community = view.xRecycle_community
    }

    override fun createPresenter(): CommunityListPresenter? {
        communityListPresenter = CommunityListPresenter(this)
        return communityListPresenter
    }

    override fun onSuccess(communityListBean: CommunityListBean) {
        val list = communityListBean.result
        adapter = CommunityListAdapter(this.requireContext(),list)
        xRecycle_community?.adapter = adapter
        xRecycle_community?.setLoadingListener(object : XRecyclerView.LoadingListener{
            override fun onLoadMore() {
                Handler().postDelayed({
                    page = Random().nextInt(3)+1
                    communityListPresenter.onICommunityListPre(hashMap,page,10)
                    xRecycle_community?.loadMoreComplete()
                }, 1000)
            }

            override fun onRefresh() {
                Handler().postDelayed({
                    page = Random().nextInt(3)+1
                    communityListPresenter.onICommunityListPre(hashMap,page,10)
                    xRecycle_community?.refreshComplete()
                }, 1000)
            }

        })
    }

    override fun onFail() {

    }

}