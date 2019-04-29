package com.wd.tech.mvp.view.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.facebook.drawee.view.SimpleDraweeView
import com.stx.xhb.xbanner.XBanner
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.BannerShowBean
import com.wd.tech.mvp.model.bean.InfoRecommendListBean
import com.wd.tech.mvp.view.activity.DetailsActivity

import kotlinx.android.synthetic.main.infomation_banner_recy.view.*
import kotlinx.android.synthetic.main.infomation_list_recy.view.*
import java.text.SimpleDateFormat
import java.util.*
import com.wd.tech.mvp.view.activity.AdvertisementDetailActivity
import kotlinx.android.synthetic.main.information_list_advertisement_item.view.*

class BannerAdapter(
    val context: Context?,
    val bannerBean: BannerShowBean?,
    val infoRecommendListBean: InfoRecommendListBean?

) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val FORMAT_DATE_TIME_PATTERN = "HH:mm:ss"
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int):RecyclerView. ViewHolder{

        if (p1 == 3) {
            val view = LayoutInflater.from(context).inflate(R.layout.infomation_banner_recy, p0, false)
            return ViewHolder(view)
        }
        else if(p1 ==2){
            val view = LayoutInflater.from(context).inflate(R.layout.infomation_list_recy, p0, false)
            return ViewHolder1(view)
        }else
        {
            val view1 = LayoutInflater.from(context).inflate(R.layout.information_list_advertisement_item, null)
            return AdverViewHolder(view1)
        }

    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return 3
        } else  {
            val get = infoRecommendListBean!!.result!!.get(position - 1)
            if (get.whetherAdvertising == 2) {
                return 2
            } else if (get.whetherAdvertising == 1) {
                return 1
            }
        }
        return 0
    }
    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        /**
         * 轮播图的展示
         */
        if (p0 is ViewHolder) {
            val images = ArrayList<String>()
            val titles = ArrayList<String>()

            images.add(bannerBean?.result?.get(0)?.imageUrl!!)
            images.add(bannerBean?.result?.get(1)?.imageUrl!!)
            images.add(bannerBean.result.get(2).imageUrl)
            images.add(bannerBean.result.get(3).imageUrl)
            images.add(bannerBean.result.get(4).imageUrl)
            images.add(bannerBean.result.get(5).imageUrl)
            titles.add(bannerBean.result.get(0).title)
            titles.add(bannerBean.result.get(1).title)
            titles.add(bannerBean.result.get(2).title)
            titles.add(bannerBean.result.get(3).title)
            titles.add(bannerBean.result.get(4).title)
            titles.add(bannerBean.result.get(5).title)
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
        /**
         * 咨询展示的展示
         */
        if (p0 is ViewHolder1) {
                p0.itemView.info_title.text= infoRecommendListBean?.result?.get(p1-1)?.title
                p0.itemView.info_summary.text=infoRecommendListBean!!.result!!.get(p1-1).summary
                p0.itemView.info_share_num.text= infoRecommendListBean.result!!.get(p1-1).share.toString()
                p0.itemView.info_zan_num.text=infoRecommendListBean.result!!.get(p1-1).collection.toString()
                p0.itemView.info_img.setImageURI(infoRecommendListBean.result!!.get(p1-1).thumbnail)
                var dateFormat= SimpleDateFormat(FORMAT_DATE_TIME_PATTERN, Locale.getDefault())
                p0.itemView.info_time.setText(dateFormat.format(infoRecommendListBean.result?.get(p1-1)?.releaseTime))
                p0.itemView.info_source.text= infoRecommendListBean.result!!.get(p1-1).source
                //收藏的判断
                if (infoRecommendListBean.result!!.get(p1-1).whetherCollection==2)
                {

                    p0.itemView.info_zan.setImageResource(R.mipmap.common_icon_collect)
                }else{

                    p0.itemView.info_zan.setImageResource(R.mipmap.common_icon_collect_s)
                }
                //判断是否需要购买
                if(infoRecommendListBean!!.result!!.get(p1-1).whetherPay == 2)
                {
                    p0.itemView.pay_img.visibility =View.GONE
                }else{
                    p0.itemView.pay_img.visibility =View.VISIBLE
                }
               //条目点击  进入详情
              p0.itemView.setOnClickListener {

                    var intent = Intent(context, DetailsActivity::class.java)
                    intent.putExtra("id", infoRecommendListBean!!.result!!.get(p1-1).id)
                    intent.putExtra("whetherPay",infoRecommendListBean.result?.get(p1-1)?.whetherPay)
                    intent.putExtra("whetherCollection",infoRecommendListBean.result?.get(p1-1)?.whetherCollection)

                    context!!.startActivity(intent)
                }
            // 收藏
            p0.itemView.info_zan.setOnClickListener(object :View.OnClickListener{
                override fun onClick(v: View?) {
                    Log.e("nhj",infoRecommendListBean!!.result!!.get(p1-1).id.toString())
                    if(mOnClick!=null)
                    {
                        Log.e("lallalalla收藏",infoRecommendListBean!!.result!!.get(p1-1).id.toString())
                        mOnClick?.getdata(infoRecommendListBean!!.result!!.get(p1-1).id,infoRecommendListBean!!.result!!.get(p1-1).whetherCollection!!,p1)
                    }
                }
            })
            //点击分享
            p0.itemView.info_share.setOnClickListener(object :View.OnClickListener{
                override fun onClick(v: View?) {
                    Log.e("lallalalla分享",infoRecommendListBean!!.result!!.get(p1-1).id.toString())
                    if (monShareClick!=null){
                        monShareClick!!.onShareClickLisenter(infoRecommendListBean!!.result!!.get(p1-1).id)
                    }
                }
            })

        }
        /**
         * 广告的展示
         */
        if(p0 is AdverViewHolder)
        {
            p0. itemView.information_list_adv_centext.setText(infoRecommendListBean?.result?.get(p1-1)?.infoAdvertisingVo?.content)
            p0. itemView.information_list_adv_image.setImageURI(infoRecommendListBean?.result?.get(p1-1)?.infoAdvertisingVo!!.pic)
            p0.itemView.setOnClickListener {
                val intent = Intent(context, AdvertisementDetailActivity::class.java)
                intent.putExtra("aid", infoRecommendListBean.result?.get(p1-1)?.infoAdvertisingVo?.id)
                intent.putExtra("content", infoRecommendListBean.result?.get(p1-1)?.infoAdvertisingVo?.content)
                intent.putExtra("url", infoRecommendListBean.result?.get(p1-1)?.infoAdvertisingVo?.url)
                context?.startActivity(intent)
            }
        }
    }
    override fun getItemCount(): Int {
        if (bannerBean?.result?.size!=0&&infoRecommendListBean?.result?.size!=0) {
            return infoRecommendListBean?.result!!.size+1
        }else if (bannerBean?.result?.size!=0&&infoRecommendListBean?.result?.size==0){
            return 1
        }
        return 0
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var banner: XBanner? = null
        init {
            banner = itemView.banner
        }
    }
    class ViewHolder1(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var info_title: TextView
        var info_img: SimpleDraweeView
        var info_summary: TextView
        var info_source: TextView
        var info_time: TextView
        var info_share_num: TextView
        var info_share:ImageView
        var info_zan_num: TextView
        var info_zan:ImageView
        var pay_img:ImageView
        init {
            info_title = itemView.info_title
            info_summary = itemView.info_summary
            info_source = itemView.info_source
            info_time = itemView.info_time
            info_share_num = itemView.info_share_num
            info_zan_num = itemView.info_zan_num
            info_img = itemView.info_img
            info_share = itemView.info_share
            info_zan = itemView.info_zan
            pay_img = itemView.pay_img
        }
    }
    class AdverViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var information_list_adv_centext: TextView
        var information_list_adv_image: SimpleDraweeView

        init {
            information_list_adv_centext = itemView.information_list_adv_centext
            information_list_adv_image = itemView.information_list_adv_image
        }
    }

    /**
     * 点赞的接口回调
     */
    var mOnClick: OnClick?=null
    fun setOnClick(mOnClick: OnClick) {
        this.mOnClick = mOnClick
    }
    interface OnClick {
        fun getdata(id: Int, great: Int, position: Int)
    }
    //收藏改变
    fun getlike(position: Int) {
        infoRecommendListBean!!.result!!.get(position).whetherCollection=1
        infoRecommendListBean!!.result!!.get(position).collection=infoRecommendListBean.result!!.get(position).collection+1
        notifyDataSetChanged()
    }
    //收藏改变
    fun getcancel(position: Int) {
        infoRecommendListBean!!.result!!.get(position).whetherCollection=2
        infoRecommendListBean!!.result!!.get(position).collection=infoRecommendListBean.result!!.get(position).collection-1
        notifyDataSetChanged()
    }
    /**
     * 分享的接口回调
     */
    //分享的接口回调
    var monShareClick: onShareClick?=null

    fun setOnShareClickLisenter(onShareClick: onShareClick) {
        monShareClick = onShareClick
    }

    interface onShareClick {
        fun onShareClickLisenter(id: Int)
    }

}

