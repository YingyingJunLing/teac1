package com.wd.tech.mvp.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.InfoCollectionBeanResult
import com.wd.tech.mvp.model.utils.FrescoUtil
import kotlinx.android.synthetic.main.item_collection_list.view.*
import java.text.SimpleDateFormat
import java.util.*

class CollectionListAdapter(context: Context,list: List<InfoCollectionBeanResult>) : RecyclerView.Adapter<CollectionListAdapter.MyViewHolder>() {

    var context : Context = context
    var list : List<InfoCollectionBeanResult> = list

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        var view : View = LayoutInflater.from(context).inflate(R.layout.item_collection_list,null)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        FrescoUtil.setPic(list.get(p1).thumbnail,p0.collection_image)
        p0.collection_title.setText(list.get(p1).title)
        val currentTime = Date()
        val simpleTiem = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val time = simpleTiem.format(list.get(p1).createTime)
        p0.collection_writer.setText(time)
    }

    class MyViewHolder : RecyclerView.ViewHolder {

        var collection_image : SimpleDraweeView
        var collection_title : TextView
        var collection_writer : TextView

        constructor(view:View) : super(view){
            collection_image = view.collection_image
            collection_title = view.collection_title
            collection_writer = view.collection_writer
        }
    }
}