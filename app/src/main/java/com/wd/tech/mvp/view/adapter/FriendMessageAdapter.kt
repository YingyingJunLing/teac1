package com.wd.tech.mvp.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cn.jpush.im.android.api.JMessageClient
import cn.jpush.im.android.api.content.ImageContent
import cn.jpush.im.android.api.enums.ContentType
import cn.jpush.im.android.api.model.Message
import com.bumptech.glide.Glide
import com.facebook.drawee.view.SimpleDraweeView
import com.wd.tech.R
import com.wd.tech.mvp.model.utils.FrescoUtil
import com.wd.tech.mvp.view.activity.MainActivity
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

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        val head_img = list.get(p1).fromUser.address
        var width = MainActivity.width
        var imagePara = p0.message_image.layoutParams
        imagePara.width = width - 280
        imagePara.height = width - 280
        p0.message_image.setLayoutParams(imagePara)
        when(list.get(p1).content.contentType){
            ContentType.text ->
                if (JMessageClient.getMyInfo().userName == list.get(p1).fromUser.userName){
                    //文字处理
                    FrescoUtil.setPic(head_img,p0.mine_img)
                    p0.view_friend.visibility = View.GONE
                    p0.view_mine.visibility = View.VISIBLE
                    p0.message_image.visibility = View.GONE
                    val content = list.get(p1).content.toJson().split("\"")
                    p0.message_text.setText(content.get(3))
                    p0.message_image.visibility = View.GONE
                }else{
                    //文字处理
                    FrescoUtil.setPic(head_img,p0.friend_img)
                    p0.view_friend.visibility = View.VISIBLE
                    p0.view_mine.visibility = View.GONE
                    p0.message_image.visibility = View.GONE
                    val content = list.get(p1).content.toJson().split("\"")
                    p0.message_text.setText(content.get(3))
                    p0.message_image.visibility = View.GONE
                }
            ContentType.image ->
                if (JMessageClient.getMyInfo().userName == list.get(p1).fromUser.userName){
                    //图片处理
                    val imageContent : ImageContent  = list.get(p1).content as ImageContent
                    imageContent.localPath
                    val localThumbnailPath = imageContent.localThumbnailPath
                    FrescoUtil.setPic(head_img,p0.mine_img)
                    p0.view_mine.visibility = View.GONE
                    p0.view_friend.visibility = View.GONE
                    p0.message_text.visibility = View.GONE
                    Glide.with(context).load(localThumbnailPath).into(p0.message_image)
                }else{
                    //图片处理
                    val imageContent : ImageContent  = list.get(p1).content as ImageContent
                    imageContent.localPath
                    val localThumbnailPath = imageContent.localThumbnailPath
                    FrescoUtil.setPic(head_img,p0.friend_img)
                    p0.view_mine.visibility = View.GONE
                    p0.view_friend.visibility = View.GONE
                    p0.message_text.visibility = View.GONE
                    Glide.with(context).load(localThumbnailPath).into(p0.message_image)
                }
            ContentType.voice ->
                if (JMessageClient.getMyInfo().userName == list.get(p1).fromUser.userName){
                    //语音处理
                    FrescoUtil.setPic(head_img,p0.mine_img)
                    p0.view_mine.visibility = View.GONE
                    p0.view_friend.visibility = View.GONE
                    p0.message_text.visibility = View.GONE
                }else{
                    //语音处理
                    FrescoUtil.setPic(head_img,p0.friend_img)
                    p0.view_mine.visibility = View.GONE
                    p0.view_friend.visibility = View.GONE
                    p0.message_text.visibility = View.GONE
                }
        }
    }

    class MyViewHolder : RecyclerView.ViewHolder {

        var message_text : TextView
        var friend_img : SimpleDraweeView
        var mine_img : SimpleDraweeView
        var view_friend : View
        var view_mine : View
        var message_image : ImageView

        constructor(view : View) : super(view){
            message_text = view.message_text
            friend_img = view.friend_img
            mine_img = view.mine_img
            view_mine = view.view_mine
            view_friend = view.view_friend
            message_image = view.message_image
        }
    }
}