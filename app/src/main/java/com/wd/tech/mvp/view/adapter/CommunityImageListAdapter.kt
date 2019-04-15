package com.wd.tech.mvp.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.wd.tech.mvp.view.activity.MainActivity
import kotlinx.android.synthetic.main.community_image_list_layout.view.*


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

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        var width = MainActivity.width
        var height = MainActivity.height
        if (list.size <= 1){
            var para = p0.community_image_show.layoutParams
            para.width = width
            para.height = height
            Log.i("宽高",para.width.toString()+"---"+para.height.toString())
            p0.community_image_show.setLayoutParams(para)
            Glide.with(context).load(flie.split(",")[p1]).into(p0.community_image_show)
        }else if (list.size == 2){
            var para = p0.community_image_show.layoutParams
            para.width = width/2
            para.height = height/2
            Log.i("宽高",para.width.toString()+"---"+para.height.toString())
            p0.community_image_show.setLayoutParams(para)
            Glide.with(context).load(flie.split(",")[p1]).into(p0.community_image_show)
        }else{
            var para = p0.community_image_show.layoutParams
            para.width = width/3
            para.height = height/3
            Log.i("宽高",para.width.toString()+"---"+para.height.toString())
            p0.community_image_show.setLayoutParams(para)
            Glide.with(context).load(flie.split(",")[p1]).into(p0.community_image_show)
        }
    }

    class MyViewHolder : RecyclerView.ViewHolder{

        var community_image_show : ImageView

        constructor(view : View) : super(view){
            community_image_show = view.community_image_show
        }
    }
}