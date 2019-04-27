package com.wd.tech.mvp.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cn.jpush.im.android.api.JMessageClient
import cn.jpush.im.android.api.model.Message
import com.facebook.drawee.view.SimpleDraweeView
import com.wd.tech.R
import com.wd.tech.mvp.model.utils.FrescoUtil
import kotlinx.android.synthetic.main.friend_message_item_layout.view.*

class FriendMessageAdapter(context: Context, list: MutableList<Message>) : RecyclerView.Adapter<FriendMessageAdapter.MyViewHolder>(){

    var context : Context = context
    var list : MutableList<Message> = list

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        var view : View = LayoutInflater.from(context).inflate(R.layout.friend_message_item_layout,null)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        val head_img = list.get(p1).fromUser.address
        val content = list.get(p1).content.toJson().split("\"")
        p0.message_text.setText(content.get(3))
        if (JMessageClient.getMyInfo().userName == list.get(p1).fromUser.userName){
            FrescoUtil.setPic(head_img,p0.mine_img)
            p0.view_friend.visibility = View.GONE
        }else{
            FrescoUtil.setPic(head_img,p0.friend_img)
            p0.view_mine.visibility = View.GONE
        }
    }

    class MyViewHolder : RecyclerView.ViewHolder {

        var message_text : TextView
        var friend_img : SimpleDraweeView
        var mine_img : SimpleDraweeView
        var view_friend : View
        var view_mine : View

        constructor(view : View) : super(view){
            message_text = view.message_text
            friend_img = view.friend_img
            mine_img = view.mine_img
            view_mine = view.view_mine
            view_friend = view.view_friend

        }
    }
}