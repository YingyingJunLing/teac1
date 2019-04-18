package com.wd.tech.mvp.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.wd.tech.R

class UserPushImageAdapter(context: Context,list: List<String>) : RecyclerView.Adapter<UserPushImageAdapter.MyViewHolder>() {

    var context : Context = context
    var list : List<String> = list

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        var view : View = View.inflate(context, R.layout.item_collection_list,null)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {

    }

    class MyViewHolder : RecyclerView.ViewHolder {
        constructor(view: View) : super(view){

        }
    }
}