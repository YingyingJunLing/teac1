package com.wd.tech.mvp.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.CommunityListBeanResult
import com.wd.tech.mvp.model.utils.FrescoUtil
import kotlinx.android.synthetic.main.item_community.view.*
import java.text.SimpleDateFormat
import java.util.*

class CommunityListAdapter(context : Context, list : List<CommunityListBeanResult>) : RecyclerView.Adapter<CommunityListAdapter.MyViewHolder>() {
    var context : Context = context
    var list : List<CommunityListBeanResult> = list

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        var view : View = LayoutInflater.from(context).inflate(R.layout.item_community,p0,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        FrescoUtil.setPic(list.get(p1).headPic,p0.user_head_image)
        p0.user_head_text.setText(list.get(p1).nickName)
        val currentTime = Date()
        val simpleTiem = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val time = simpleTiem.format(currentTime)
        p0.user_head_time.setText(time)
        p0.user_head_text_title.setText(list.get(p1).content)
        var file : String = list.get(p1).file
        var length=file.split(",")
        for (i in length){
            var imageView : ImageView = ImageView(context)
        }
    }

    class MyViewHolder : RecyclerView.ViewHolder{
        var user_head_image : SimpleDraweeView
        var user_head_text : TextView
        var user_head_time : TextView
        var user_head_text_title : TextView
        var user_image_linear : LinearLayout
        constructor(view : View) : super(view){
            user_head_image = view.user_head_image
            user_head_text = view.user_head_text
            user_head_time = view.user_head_time
            user_head_text_title = view.user_head_text_title
            user_image_linear = view.user_image_linear
        }
    }
}