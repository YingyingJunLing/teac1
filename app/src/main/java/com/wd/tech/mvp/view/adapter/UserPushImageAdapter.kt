package com.wd.tech.mvp.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.wd.tech.R
import com.wd.tech.mvp.view.activity.MainActivity
import kotlinx.android.synthetic.main.community_image_list_layout.view.*
import java.io.File

class UserPushImageAdapter(context: Context,list: List<String>) : RecyclerView.Adapter<UserPushImageAdapter.MyViewHolder>() {

    var context : Context = context
    var list : List<String> = list

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        var view : View = View.inflate(context, R.layout.community_image_list_layout,null)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size+1
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        var width = MainActivity.width
        var para = p0.community_image_show.layoutParams
        para.width = (width/3)-20
        para.height = (width/3)-20
        p0.community_image_show.setLayoutParams(para)
        p0.community_image_show.scaleType = ImageView.ScaleType.CENTER_CROP
        if (p1 == list.size){
            p0.community_image_show.setImageResource(R.mipmap.common_nav_btn_add_n)
            p0.community_image_show.setOnClickListener(object : View.OnClickListener{
                override fun onClick(v: View?) {
                    iClickListener.setIClickListener()
                }
            })
        }else{
            val image = list.get(p1)
            Glide.with(context).load(File(image)).into(p0.community_image_show)
        }
    }

    class MyViewHolder : RecyclerView.ViewHolder {

        var community_image_show : ImageView

        constructor(view : View) : super(view){
            community_image_show = view.community_image_show
        }
    }

    interface IClickListener{
        fun setIClickListener()
    }

    lateinit var iClickListener : IClickListener

    fun setUserPushImageOnClick(iClickListener : IClickListener){
        this.iClickListener = iClickListener
    }
}