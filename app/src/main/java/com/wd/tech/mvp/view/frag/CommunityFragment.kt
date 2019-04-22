package com.wd.tech.mvp.view.frag

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.CommunityListBean
import com.wd.tech.mvp.presenter.presenterimpl.CommunityListPresenter
import com.wd.tech.mvp.view.activity.UserPushCommentActivity
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
    var loading_linear : LinearLayout ?= null
    var page : Int = Random().nextInt(5)+1

    override fun setView(): Int {
        return R.layout.frag_community
    }

    override fun initFragmentData(savedInstanceState: Bundle?) {
        var sharedPreferences : SharedPreferences = activity!!.getSharedPreferences("User", Context.MODE_PRIVATE)
        var userId = sharedPreferences.getString("userId", "0")
        var sessionId = sharedPreferences.getString("sessionId", "0")
        hashMap.put("userId",userId)
        hashMap.put("sessionId",sessionId)
        communityListPresenter.onICommunityListPre(hashMap,page,10)
    }

    override fun initFragmentChildView(view: View) {
        view.xRecycle_community.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        xRecycle_community = view.xRecycle_community
        loading_linear = view.loading_linear
        view.user_push_comment_image.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var intent : Intent = Intent(activity,UserPushCommentActivity::class.java)
                startActivity(intent)
            }
        })
    }

    override fun createPresenter(): CommunityListPresenter? {
        communityListPresenter = CommunityListPresenter(this)
        return communityListPresenter
    }

    override fun onSuccess(communityListBean: CommunityListBean) {
        xRecycle_community?.visibility = View.VISIBLE
        loading_linear?.visibility = View.GONE
        val list = communityListBean.result
        xRecycle_community?.adapter = context?.let { CommunityListAdapter(it,list) }
        xRecycle_community?.setLoadingListener(object : XRecyclerView.LoadingListener{
            override fun onLoadMore() {
                Handler().postDelayed({
                    page = Random().nextInt(5)+1
                    communityListPresenter.onICommunityListPre(hashMap,page,10)
                    xRecycle_community?.loadMoreComplete()
                }, 1000)
            }

            override fun onRefresh() {
                Handler().postDelayed({
                    page = 1
                    communityListPresenter.onICommunityListPre(hashMap,page,10)
                    xRecycle_community?.refreshComplete()
                }, 1000)
            }

        })
    }

    override fun onFail() {

    }

    override fun onDestroy() {
        super.onDestroy()
        if (communityListPresenter != null){
            communityListPresenter.detachView()
        }
    }
}