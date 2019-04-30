package com.wd.tech.mvp.view.frag

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Toast
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.FriendGroupListBean
import com.wd.tech.mvp.model.bean.FriendGroupListBeanResult
import com.wd.tech.mvp.model.bean.FriendListGroupByIdBean
import com.wd.tech.mvp.model.bean.InitFriendListBean
import com.wd.tech.mvp.presenter.presenterimpl.MessagePresenter
import com.wd.tech.mvp.view.activity.NewFriendListActivity
import com.wd.tech.mvp.view.adapter.GroupPeoplestListAdapter
import com.wd.tech.mvp.view.base.BaseFragment
import com.wd.tech.mvp.view.contract.Contract
import kotlinx.android.synthetic.main.frag_message_people_layout.view.*

class FragMessagePeopleList : BaseFragment<Contract.IMessageView, MessagePresenter>(), Contract.IMessageView{

    var messagePresenter : MessagePresenter = MessagePresenter(this)
    var hashMap : HashMap<String,String> = HashMap()
    var people_group_recycle : RecyclerView ?= null

    override fun setView(): Int {
        return R.layout.frag_message_people_layout
    }

    override fun initFragmentData(savedInstanceState: Bundle?) {
        var sharedPreferences : SharedPreferences = activity!!.getSharedPreferences("User", Context.MODE_PRIVATE)
        var userId = sharedPreferences.getString("userId", "0")
        var sessionId = sharedPreferences.getString("sessionId", "0")
        hashMap.put("userId",userId)
        hashMap.put("sessionId",sessionId)
        messagePresenter.onIMessagePre(hashMap)
    }

    override fun initFragmentChildView(view: View) {
        view.people_group_recycle.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        people_group_recycle = view.people_group_recycle
        view.new_friend_linear.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var intent : Intent = Intent(activity,NewFriendListActivity::class.java)
                startActivity(intent)
            }
        })
    }

    override fun createPresenter(): MessagePresenter? {
        return messagePresenter
    }

    override fun onSuccess(initFriendListBean: InitFriendListBean) {
        if (initFriendListBean.status=="0000"){
            people_group_recycle?.adapter = context?.let { GroupPeoplestListAdapter(it,initFriendListBean.result) }
        }else{
            Toast.makeText(activity,initFriendListBean.message,Toast.LENGTH_LONG).show()
        }
    }

    override fun onFail() {

    }

    override fun onDestroy() {
        super.onDestroy()
        messagePresenter.detachView()
    }
}