package com.wd.tech.mvp.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.wd.tech.mvp.view.activity.MainActivity
import kotlinx.android.synthetic.main.community_image_list_layout.view.*

/**
 * 图片加载adapter
 */
class CommunityImageListAdapter(context : Context, list : List<String>,flie : String) : RecyclerView.Adapter<CommunityImageListAdapter.MyViewHolder>() {

    private var context : Context = context
    private var list : List<String> = list
    private var flie : String = flie

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        var view : View = View.inflate(context, com.wd.tech.R.layout.community_image_list_layout,null)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        /**
         * 加载图片 根据图片数量进行判断
         */
        var width = MainActivity.width
        var imagePara = p0.community_image_show.layoutParams
        var imageHeight : Int = imagePara.height
        var para = p0.community_image_show.layoutParams
        if (list.size <= 0){
            p0.community_image_show.visibility = View.GONE
        }else if (list.size == 1){
            para.width = width
            para.height = imageHeight
            p0.community_image_show.setLayoutParams(para)
        }else if (list.size == 2){
            para.width = (width/2)-20
            para.height = (width/2)-20
            p0.community_image_show.setLayoutParams(para)
            p0.community_image_show.scaleType = ImageView.ScaleType.CENTER_CROP
        }else{
            para.width = (width/3)-20
            para.height = (width/3)-20
            p0.community_image_show.setLayoutParams(para)
            p0.community_image_show.scaleType = ImageView.ScaleType.CENTER_CROP
        }
        Glide.with(context).load(flie.split(",")[p1]).into(p0.community_image_show)
    }

    class MyViewHolder : RecyclerView.ViewHolder{

        var community_image_show : ImageView

        constructor(view : View) : super(view){
            community_image_show = view.community_image_show
        }
    }
}