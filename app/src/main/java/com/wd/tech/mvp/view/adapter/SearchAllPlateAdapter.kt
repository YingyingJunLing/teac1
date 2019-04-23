package com.wd.tech.mvp.view.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.InfoRecommendListBean
import com.wd.tech.mvp.view.activity.DetailsActivity
import com.wd.tech.mvp.view.activity.SearchAllPlateActivity
import kotlinx.android.synthetic.main.infomation_list_recy.view.*
import kotlinx.android.synthetic.main.search_all_plate_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class SearchAllPlateAdapter(
    val context: Context,
    val any: InfoRecommendListBean
) : RecyclerView.Adapter<SearchAllPlateAdapter.ViewHolder>() {
    val FORMAT_DATE_TIME_PATTERN = "HH:mm:ss"
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.search_all_plate_item, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return any.result!!.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.itemView.search_all_plate_item_source.text= any.result!!.get(p1).source
        p0.itemView.search_all_plate_item_title.text = any.result!!.get(p1).title
        var dateFormat= SimpleDateFormat(FORMAT_DATE_TIME_PATTERN, Locale.getDefault())
        p0.itemView.search_all_plate_item_time.setText(dateFormat.format(any.result?.get(p1)?.releaseTime))
        p0.itemView.setOnClickListener {
            var intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra("id", any!!.result!!.get(p1).id)
            intent.putExtra("whetherPay",any.result?.get(p1)?.whetherPay)
            intent.putExtra("whetherCollection",any.result?.get(p1)?.whetherCollection)
            context!!.startActivity(intent)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}