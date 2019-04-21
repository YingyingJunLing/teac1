package com.wd.tech.mvp.view.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.InfoRecommendListBean

import com.wd.tech.mvp.view.activity.DetailsActivity

import kotlinx.android.synthetic.main.infomation_list_recy.view.*
import java.text.SimpleDateFormat
import java.util.*

class AllPlateItemAdapter(val context: Context , val  infoRecommendListBean: InfoRecommendListBean):
    RecyclerView.Adapter<AllPlateItemAdapter.ViewHolder>() {
    val FORMAT_DATE_TIME_PATTERN = "HH:mm:ss"
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.infomation_list_recy, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
      return infoRecommendListBean.result!!.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        if (infoRecommendListBean!!.result!![p1].whetherAdvertising==2){
            p0.itemView.info_img.visibility=View.VISIBLE
            p0.itemView.info_title.visibility=View.VISIBLE
            p0.itemView.info_summary.visibility=View.VISIBLE
            p0.itemView.info_title.text= infoRecommendListBean?.result?.get(p1)?.title
            p0.itemView.info_summary.text=infoRecommendListBean!!.result!!.get(p1).summary
            p0.itemView.info_share_num.text= infoRecommendListBean.result!!.get(p1).share.toString()
            p0.itemView.info_zan_num.text=infoRecommendListBean.result!!.get(p1).collection.toString()
            p0.itemView.info_img.setImageURI(infoRecommendListBean.result!!.get(p1).thumbnail)
            var dateFormat= SimpleDateFormat(FORMAT_DATE_TIME_PATTERN, Locale.getDefault())
            p0.itemView.info_time.setText(dateFormat.format(infoRecommendListBean.result?.get(p1)?.releaseTime))
            p0.itemView.info_source.text= infoRecommendListBean.result!!.get(p1).source
            if (infoRecommendListBean.result!!.get(p1).whetherCollection==2)
            {
                p0.itemView.info_zan.setImageResource(R.mipmap.common_icon_collect)
            }else{
                p0.itemView.info_zan.setImageResource(R.mipmap.common_icon_collect_s)
            }
            //点赞按钮  收藏
            p0.itemView.info_zan.setOnClickListener(object :View.OnClickListener{
                override fun onClick(v: View?) {
                    if(mOnClick!=null)
                    {
                        mOnClick?.getdata(infoRecommendListBean!!.result!!.get(p1).id,infoRecommendListBean!!.result!!.get(p1).whetherCollection!!,p1)
                    }
                }
            })
            if(infoRecommendListBean!!.result!!.get(p1).whetherPay == 2)
            {
                p0.itemView.pay_img.visibility =View.GONE
            }else{
                p0.itemView.pay_img.visibility =View.VISIBLE
            }
            p0.itemView.setOnClickListener {
                var intent = Intent(context, DetailsActivity::class.java)
                intent.putExtra("id", infoRecommendListBean!!.result!!.get(p1).id)
                intent.putExtra("whetherPay",infoRecommendListBean.result?.get(p1)?.whetherPay)
                context!!.startActivity(intent)
            }
        }
        //点赞按钮  收藏
        p0.itemView.info_zan.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                Log.e("lallalalla",infoRecommendListBean!!.result!!.get(p1).id.toString())
                if(mOnClick!=null)
                {
                    Log.e("lallalalla",infoRecommendListBean!!.result!!.get(p1).id.toString())
                    mOnClick?.getdata(infoRecommendListBean!!.result!!.get(p1).id,infoRecommendListBean!!.result!!.get(p1).whetherCollection!!,p1)
                }
            }
        })
        if(infoRecommendListBean!!.result!!.get(p1).whetherPay == 2)
        {
            p0.itemView.pay_img.visibility =View.GONE
        }else{
            p0.itemView.pay_img.visibility =View.VISIBLE
        }
        p0.itemView.setOnClickListener {
            var intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra("id", infoRecommendListBean!!.result!!.get(p1).id)
            intent.putExtra("whetherPay",infoRecommendListBean!!.result?.get(p1)?.whetherPay)
            context!!.startActivity(intent)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var info_title: TextView
        var info_img: SimpleDraweeView
        var info_summary: TextView
        var info_source: TextView
        var info_time: TextView
        var info_share_num: TextView
        var info_share: ImageView
        var info_zan_num: TextView
        var info_zan: ImageView
        var pay_img: ImageView
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
    var mOnClick: OnClick? = null
    fun setOnClick(mOnClick: OnClick) {
        this.mOnClick = mOnClick
    }
    interface OnClick {
        fun getdata(id: Int, great: Int, position: Int)
    }
    //点赞改变
    fun getlike(position: Int) {
        infoRecommendListBean!!.result!!.get(position).whetherCollection=1
        infoRecommendListBean!!.result!!.get(position).collection=infoRecommendListBean.result!!.get(position).collection+1
        notifyDataSetChanged()
    }

    //取消点赞改变
    fun getcancel(position: Int) {
        infoRecommendListBean!!.result!!.get(position).whetherCollection=2
        infoRecommendListBean!!.result!!.get(position).collection=infoRecommendListBean.result!!.get(position).collection-1
        notifyDataSetChanged()
    }
}