package com.wd.tech.mvp.view.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.FindAllInfoPlate

import com.wd.tech.mvp.view.activity.SearchAllPlateActivity
import kotlinx.android.synthetic.main.searchplate.view.*

class SearchAllItemAdapters(
    val context: Context,
    val any: FindAllInfoPlate
): RecyclerView.Adapter<SearchAllItemAdapters.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.searchplate, p0, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return any.getResult()!!.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
       p0.itemView. search_plate_name.text= any.getResult()?.get(p1)?.name
        p0.itemView.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
             Toast.makeText(context,  any.getResult()?.get(p1)?.name,Toast.LENGTH_LONG).show()
                val intent = Intent(context, SearchAllPlateActivity::class.java)
                intent.putExtra("id", any!!.getResult()!!.get(p1).id)
                intent.putExtra("name",any.getResult()?.get(p1)?.name)
                context.startActivity(intent)
            }
        })
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}