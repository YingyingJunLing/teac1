package com.wd.tech.mvp.view.frag

import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.CommunityListBean
import com.wd.tech.mvp.presenter.presenterimpl.CommunityListPresenter
import com.wd.tech.mvp.view.base.BaseFragment
import com.wd.tech.mvp.view.contract.Contract
import kotlinx.android.synthetic.main.frag_community.view.*

class CommunityFragment : BaseFragment<Contract.ICommunityListView,CommunityListPresenter>(),Contract.ICommunityListView
{

    private val v: View
        get() {
            var view: View = View.inflate(context, R.layout.frag_community, null)
            return view
        }

    override fun initFragmentView(inflater: LayoutInflater): View {
        return v
    }

    override fun initFragmentChildView() {
        v.xRecycle_community.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
    }

    override fun initFragmentData() {
        var hashMap : HashMap<String,Int> = HashMap()
        var communityListPresenter : CommunityListPresenter = CommunityListPresenter(this)
        communityListPresenter.onICommunityListPre(hashMap,1,10)
    }

    override fun onSuccess(communityListBean: CommunityListBean) {
        
    }

    override fun onFail() {

    }
}