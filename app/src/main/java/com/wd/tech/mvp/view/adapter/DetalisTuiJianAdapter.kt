package com.wd.tech.mvp.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.InfoDetailBean
import kotlinx.android.synthetic.main.detail_tuijian_item.view.*

class DetalisTuiJianAdapter(val context: Context, val any: InfoDetailBean) :
    RecyclerView.Adapter<DetalisTuiJianAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.detail_tuijian_item, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return any.result!!.informationList!!.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.itemView.detail_tuijian_img.setImageURI(any.result!!.informationList!!.get(p1).thumbnail)
        p0.itemView.detail_tuijian_title.text=any.result!!.informationList!!.get(p1).title
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}