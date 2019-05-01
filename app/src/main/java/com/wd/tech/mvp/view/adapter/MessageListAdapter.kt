package com.wd.tech.mvp.view.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cn.jpush.im.android.api.model.Conversation
import cn.jpush.im.android.api.model.Message
import com.bumptech.glide.Glide
import com.facebook.drawee.view.SimpleDraweeView
import com.wd.tech.R
import com.wd.tech.mvp.model.utils.FrescoUtil
import com.wd.tech.mvp.view.activity.FriendMessageActivity
import kotlinx.android.synthetic.main.message_layout_item.view.*

class MessageListAdapter(context: Context, list: MutableList<Conversation>) : RecyclerView.Adapter<MessageListAdapter.MyViewHolder>() {

    var context : Context = context
    var list : MutableList<Conversation> = list

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        var view : View = LayoutInflater.from(context).inflate(R.layout.message_layout_item,null)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        Glide.with(context).load(R.mipmap.nav_btn_setting).into(p0.friend_head)
        var targetId = list.get(p1).targetId
        p0.message_text.setText(list.get(p1).title)
        Log.i("用户信息",list.get(p1).toString())
        p0.message_text.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var intent : Intent = Intent(context, FriendMessageActivity::class.java)
                intent.putExtra("friendUid",targetId)
                context.startActivity(intent)
            }
        })
        if (list.get(p1).unReadMsgCnt>0){
            p0.text_num_shape.visibility = View.VISIBLE
            p0.text_num_shape.setText(list.get(p1).unReadMsgCnt.toString())
        }else{
            p0.text_num_shape.visibility = View.GONE
        }
    }

    class MyViewHolder : RecyclerView.ViewHolder {

        var message_text : TextView
        var friend_head : ImageView
        var text_num_shape : TextView

        constructor(view : View) : super(view){
            message_text=view.message_text
            friend_head=view.friend_head
            text_num_shape=view.text_num_shape
        }
    }
}