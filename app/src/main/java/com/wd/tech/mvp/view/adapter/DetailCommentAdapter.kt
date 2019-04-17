package com.wd.tech.mvp.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.DetailCommentBean
import com.wd.tech.mvp.view.adapter.DetailCommentAdapter.ViewHolder
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.detail_item_recy.view.*
import java.text.SimpleDateFormat
import java.util.*


class DetailCommentAdapter(val context: Context, val any: DetailCommentBean):
    RecyclerView.Adapter<ViewHolder>() {
    val FORMAT_DATE_TIME_PATTERN = "HH:mm:ss"
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.detail_item_recy, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return any.result.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        //头像
        p0.itemView.detail_comment_recy_hedaer.setImageURI(any.result.get(p1).headPic)
        //内容
        p0.itemView.detail_comment_recy_content.text = any.result.get(p1).content
        //时间
        var dateFormat= SimpleDateFormat(FORMAT_DATE_TIME_PATTERN, Locale.getDefault())
        p0.itemView.detail_comment_recy_time.setText(dateFormat.format(any.result.get(p1).commentTime))
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}