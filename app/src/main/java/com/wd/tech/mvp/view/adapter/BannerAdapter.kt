package com.wd.tech.mvp.view.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.stx.xhb.xbanner.XBanner
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.BannerShowBean
import com.wd.tech.mvp.model.bean.InfoRecommendListBean

import kotlinx.android.synthetic.main.infomation_banner_recy.view.*
import kotlinx.android.synthetic.main.infomation_infolist_recy.view.*

class BannerAdapter(
    val context: Context?,
    val bannerShowBean: BannerShowBean?, val infoRecommendListBean: InfoRecommendListBean?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return position % 2
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int):RecyclerView. ViewHolder{
        if (p1 == 0) {
            val view = LayoutInflater.from(context).inflate(R.layout.infomation_banner_recy, p0, false)
            return ViewHolder(view)
        } else {
            val view = LayoutInflater.from(context).inflate(R.layout.infomation_infolist_recy, p0, false)
            return ViewHolder1(view)
        }
    }
    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        if (p0 is ViewHolder) {
            val images = ArrayList<String>()
            val titles = ArrayList<String>()
            bannerShowBean?.result?.forEach {
                images.add(it.imageUrl!!)
                titles.add(it.title)
            }
            // 为XBanner绑定数据
            p0.banner?.setData(images, titles)
            p0.banner?.run {
                setmAdapter(XBanner.XBannerAdapter { banner, model, view, position ->
                    val roundedCorners = RoundedCorners(20)
                    val options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300)
                    if (context != null) {
                        Glide.with(context).load(images.get(position)).apply(options).into(view as ImageView)
                    }
                })
                setPageTransformer(com.stx.xhb.xbanner.transformers.Transformer.Accordion)
                // 设置XBanner页面切换的时间，即动画时长
                setPageChangeDuration(1000)
            }
            // XBanner中某一项的点击事件
            p0.banner?.setOnItemClickListener(XBanner.OnItemClickListener { banner, model, view, position ->
                Toast.makeText(
                    context,
                    "点击了第" + position + "图片",
                    Toast.LENGTH_SHORT
                ).show()
            })
        }
        if (p0 is ViewHolder1) {
            p0.info_list?.layoutManager = LinearLayoutManager(context, OrientationHelper.VERTICAL, false)
            var adapter = InfoRecommendListAdapter(context, infoRecommendListBean)
            Log.e("111111",infoRecommendListBean.toString())
            p0.info_list?.setAdapter(adapter)
        }
    }
    override fun getItemCount(): Int {
        return 2
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var banner: XBanner? = null
        init {
            banner = itemView.banner
        }
    }

    class ViewHolder1(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var info_list: RecyclerView? = null
        init {
            info_list = itemView.info_list
        }
    }
}