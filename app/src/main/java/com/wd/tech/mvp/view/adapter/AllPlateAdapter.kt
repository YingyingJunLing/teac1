package com.wd.tech.mvp.view.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.FindAllInfoPlate
import com.wd.tech.mvp.view.activity.AllPlateItemActivity
import kotlinx.android.synthetic.main.activity_search1.*
import kotlinx.android.synthetic.main.allplate_recy_item.view.*
import org.greenrobot.eventbus.EventBus


class AllPlateAdapter(
    val context: Context,
   val any: FindAllInfoPlate
) : RecyclerView.Adapter<AllPlateAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.allplate_recy_item, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return any.getResult()!!.size

    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.itemView.allPlate_recy_img.setImageURI(any.getResult()!!.get(p1).pic)
        p0.itemView.allPlate_recy_title.text= any!!.getResult()!!.get(p1).name
        p0.itemView.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
               var intent = Intent(context, AllPlateItemActivity::class.java)
                intent.putExtra("id", any!!.getResult()!!.get(p1).id)
                context.startActivity(intent)
            }
        })
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}