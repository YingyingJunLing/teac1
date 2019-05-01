package com.wd.tech.mvp.view.frag

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.jpush.im.android.api.JMessageClient
import com.wd.tech.R
import com.wd.tech.mvp.view.adapter.MessageListAdapter
import kotlinx.android.synthetic.main.frag_message_list_layout.*
import kotlinx.android.synthetic.main.frag_message_list_layout.view.*

class FragMessageShow : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view : View = View.inflate(activity, R.layout.frag_message_list_layout,null)
        view.message_recycle.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        val conversationList = JMessageClient.getConversationList()
        if (conversationList!=null){
            view.message_recycle.adapter = MessageListAdapter(activity!!,conversationList)
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        var view : View = View.inflate(activity, R.layout.frag_message_list_layout,null)
        view.message_recycle.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        val conversationList = JMessageClient.getConversationList()
        if (conversationList!=null){
            view.message_recycle.adapter = MessageListAdapter(activity!!,conversationList)
        }
    }
}