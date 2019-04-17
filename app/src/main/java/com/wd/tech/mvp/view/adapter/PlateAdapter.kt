package com.wd.tech.mvp.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.InfoDetailBean
import com.wd.tech.mvp.view.activity.DetailsActivity
import kotlinx.android.synthetic.main.detail_palte_item.view.*

class PlateAdapter(
   val context: Context ,
  val  any: InfoDetailBean
) : RecyclerView.Adapter<PlateAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.detail_palte_item, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return any.result!!.plate!!.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
       p0.itemView.detail_plate_name.text= any.result!!.plate!!.get(p1).name
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}