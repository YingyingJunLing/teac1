package com.wd.tech.mvp.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cn.jpush.im.android.api.model.Conversation
import cn.jpush.im.android.api.model.Message
import com.wd.tech.R
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
        p0.message_text.setText(list.get(p1).content.toJson())
    }

    class MyViewHolder : RecyclerView.ViewHolder {

        var message_text : TextView

        constructor(view : View) : super(view){
            message_text = view.message_text
        }
    }
}