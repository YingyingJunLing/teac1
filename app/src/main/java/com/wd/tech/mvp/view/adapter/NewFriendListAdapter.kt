package com.wd.tech.mvp.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.FindFriendNoticePageListBeanResult
import kotlinx.android.synthetic.main.new_friend_list_item_layout.view.*

class NewFriendListAdapter(context: Context, list: List<FindFriendNoticePageListBeanResult>) : RecyclerView.Adapter<NewFriendListAdapter.MyViewHolder>(){

    var context : Context = context
    var list : List<FindFriendNoticePageListBeanResult> = list

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.new_friend_list_item_layout,null)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        p0.user_notice_name.setText(list.get(p1).fromNickName)
    }

    class MyViewHolder : RecyclerView.ViewHolder {

        var user_notice_name : TextView

        constructor(view: View) : super(view){
            user_notice_name = view.user_notice_name
        }
    }
}