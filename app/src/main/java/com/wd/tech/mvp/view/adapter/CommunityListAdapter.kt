package com.wd.tech.mvp.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.CommunityListBeanResult
import kotlinx.android.synthetic.main.item_community.view.*

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
        Log.i("社区",list.get(p1).content)
        p0.user_head_text.setText(list.get(p1).content)
    }

    class MyViewHolder : RecyclerView.ViewHolder{
        var user_head_text : TextView
        constructor(view : View) : super(view){
            user_head_text = view.user_head_text
        }
    }
}