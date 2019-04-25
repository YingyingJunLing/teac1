package com.wd.tech.mvp.view.adapter

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wd.tech.R
import com.wd.tech.mvp.view.activity.MyCardActivityBean
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.my_card_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class MyCardAdapter(
    val context: Context,
    val any: List<MyCardActivityBean.ResultBean>?
): RecyclerView.Adapter<MyCardAdapter.ViewHolder>() {
    val FORMAT_DATE_TIME_PATTERN = "HH:mm:ss"
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.my_card_item, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return any!!.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
       p0.itemView. my_card_item_content.text = any!!.get(p1).content
       p0.itemView.my_card_item_pricenum.text = any!!.get(p1).praise.toString()

        var dateFormat = SimpleDateFormat(FORMAT_DATE_TIME_PATTERN, Locale.getDefault())
        var file: String = any.get(p1).file!!
        var length = file.split(",")
        p0.itemView.my_card_item_time.setText(dateFormat.format( any.get(p1).publishTime))
        if (length.size <= 0) {

            p0.itemView.my_card_image_recycle.layoutManager = GridLayoutManager(context, 0, GridLayoutManager.VERTICAL, false)
            p0.itemView.my_card_image_recycle.adapter = CommunityImageListAdapter(context, length, file)
        } else if (length.size == 1) {
            p0.itemView.my_card_image_recycle.layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
            p0.itemView.my_card_image_recycle.adapter = CommunityImageListAdapter(context, length, file)
        } else if (length.size == 2) {
            p0.itemView.my_card_image_recycle.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            p0.itemView.my_card_image_recycle.adapter = CommunityImageListAdapter(context, length, file)
        } else {
            p0.itemView.my_card_image_recycle.layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            p0.itemView.my_card_image_recycle.adapter = CommunityImageListAdapter(context, length, file)
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}