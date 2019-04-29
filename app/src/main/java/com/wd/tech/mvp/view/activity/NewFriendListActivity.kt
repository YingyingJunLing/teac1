package com.wd.tech.mvp.view.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.FindFriendNoticePageListBean
import com.wd.tech.mvp.model.bean.ReviewFriendApplyBean
import com.wd.tech.mvp.presenter.presenterimpl.NewFriendListPresenter
import com.wd.tech.mvp.view.adapter.NewFriendListAdapter
import com.wd.tech.mvp.view.base.BaseActivity
import com.wd.tech.mvp.view.contract.Contract
import kotlinx.android.synthetic.main.activity_new_friend_list.*
import java.util.HashMap

class NewFriendListActivity : BaseActivity<Contract.INewFriendListView,NewFriendListPresenter>(),Contract.INewFriendListView {

    var newFriendListPresenter : NewFriendListPresenter = NewFriendListPresenter(this)
    var hashMap: HashMap<String, String> = HashMap()
    var page : Int = 1
    lateinit var adapter : NewFriendListAdapter

    override fun createPresenter(): NewFriendListPresenter? {
        return newFriendListPresenter
    }

    override fun initActivityView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_new_friend_list)
    }

    override fun initData() {
        var sharedPreferences: SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)
        var userId = sharedPreferences.getString("userId", "0")
        var sessionId = sharedPreferences.getString("sessionId", "0")
        hashMap.put("userId", userId)
        hashMap.put("sessionId", sessionId)
        newFriendListPresenter.onINewFriendListPre(hashMap,page,5)
    }

    override fun initView() {
        new_friend_recycle.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
    }

    override fun onINewFriendListSuccess(findFriendNoticePageListBean: FindFriendNoticePageListBean) {
        if (findFriendNoticePageListBean.status == "0000"){
            val list = findFriendNoticePageListBean.result
            adapter = NewFriendListAdapter(this,list)
            new_friend_recycle.adapter = adapter
        }else{
            Toast.makeText(this,findFriendNoticePageListBean.message,Toast.LENGTH_LONG).show()
        }
    }

    override fun onIReviewFriend(reviewFriendApplyBean: ReviewFriendApplyBean) {

    }

    override fun onFail() {

    }
}
