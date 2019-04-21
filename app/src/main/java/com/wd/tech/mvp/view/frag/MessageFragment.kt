package com.wd.tech.mvp.view.frag

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.FriendGroupListBean
import com.wd.tech.mvp.presenter.presenterimpl.MessagePresenter
import com.wd.tech.mvp.view.base.BaseFragment
import com.wd.tech.mvp.view.contract.Contract

class MessageFragment : BaseFragment<Contract.IMessageView,MessagePresenter>(),Contract.IMessageView{

    var messagePresenter : MessagePresenter = MessagePresenter(this)
    var hashMap : HashMap<String,String> = HashMap()

    override fun setView(): Int {
        return R.layout.frag_message_layout
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

    }

    override fun createPresenter(): MessagePresenter? {
        return messagePresenter
    }

    override fun onSuccess(friendGroupListBean: FriendGroupListBean) {

    }

    override fun onFail() {

    }
}