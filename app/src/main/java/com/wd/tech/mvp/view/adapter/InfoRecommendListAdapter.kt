package com.wd.tech.mvp.view.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.facebook.drawee.gestures.GestureDetector
import com.facebook.drawee.view.SimpleDraweeView
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.InfoRecommendListBean
import com.wd.tech.mvp.view.activity.DetailsActivity
import kotlinx.android.synthetic.main.infomation_list_recy.view.*
import org.greenrobot.eventbus.EventBus
import java.text.SimpleDateFormat
import java.util.*

class InfoRecommendListAdapter(val context: Context?, val any: InfoRecommendListBean?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val FORMAT_DATE_TIME_PATTERN = "HH:mm:ss"
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.infomation_list_recy, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return any!!.result!!.size
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int)
    {
        if (any!!.result!![p1].whetherAdvertising==2){
            p0.itemView.info_img.visibility=View.VISIBLE
            p0.itemView.info_title.visibility=View.VISIBLE
            p0.itemView.info_summary.visibility=View.VISIBLE
            p0.itemView.gg_fresco.visibility=View.GONE

            p0.itemView.info_title.text= any?.result?.get(p1)?.title
            p0.itemView.info_summary.text=any!!.result!!.get(p1).summary
            p0.itemView.info_share_num.text= any.result!!.get(p1).share.toString()
            p0.itemView.info_zan_num.text=any.result!!.get(p1).collection.toString()
            p0.itemView.info_img.setImageURI(any.result!!.get(p1).thumbnail)
            var dateFormat= SimpleDateFormat(FORMAT_DATE_TIME_PATTERN, Locale.getDefault())
            p0.itemView.info_time.setText(dateFormat.format(any.result?.get(p1)?.releaseTime))
            p0.itemView.info_source.text= any.result!!.get(p1).source
            if (any.result!!.get(p1).whetherCollection ==2)
            {
                p0.itemView.info_zan.setImageResource(R.mipmap.common_icon_collect)
            }else{
                p0.itemView.info_zan.setImageResource(R.mipmap.common_icon_collect_s)
            }
                //点赞按钮  收藏
           p0.itemView.info_zan.setOnClickListener(object :View.OnClickListener{
               override fun onClick(v: View?) {
                   if(mOnClick !=null)
                   {
                       mOnClick!!.getdata(any.result!!.get(p1).id, any.result!!.get(p1).whetherCollection,p1)
                   }

               }
           })
            if(any.result!!.get(p1).whetherPay == 2)
            {
               p0.itemView.pay_img.visibility =View.GONE
            }else{
                p0.itemView.pay_img.visibility =View.VISIBLE
            }

            p0.itemView.setOnClickListener {
                var intent =Intent(context, DetailsActivity::class.java)
                intent.putExtra("id", any.result!!.get(p1).id)
                intent.putExtra("whetherPay",any.result?.get(p1)?.whetherPay)
                context!!.startActivity(intent)
            }
        }
        if (any!!.result!![p1].whetherAdvertising==1){
            p0.itemView.info_img.visibility=View.GONE
            p0.itemView.info_title.visibility=View.GONE
            p0.itemView.info_summary.visibility=View.GONE
            p0.itemView.gg_fresco.visibility=View.VISIBLE
            var uri : Uri =Uri.parse(any!!.result!![p1].infoAdvertisingVo!!.pic)
            p0.itemView.gg_fresco.setImageURI(uri)
        }

    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var info_title:TextView
        var info_img:SimpleDraweeView
        var info_summary:TextView
        var info_source:TextView
        var info_time:TextView
        var info_share_num:TextView
        var info_share:ImageView
        var info_zan_num:TextView
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

    private var mOnClick: OnClick? = null
    fun setOnClick(mOnClick: OnClick) {
        this.mOnClick = mOnClick
    }


    interface OnClick {
        fun getdata(id: Int, great: Int, position: Int)
    }
}


