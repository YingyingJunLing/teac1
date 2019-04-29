package com.wd.tech.mvp.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.MyNoticeBean
import com.wd.tech.mvp.view.activity.MyNoticeActivity

class MyNoticeAdapter(
   val context: Context,
   val any: MyNoticeBean
): RecyclerView.Adapter<MyNoticeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.my_notice_item, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return any.result.size

    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}