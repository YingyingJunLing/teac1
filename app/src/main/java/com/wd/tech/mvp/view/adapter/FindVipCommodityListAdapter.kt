package com.wd.tech.mvp.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.FindVipCommodityListBeanResult

import kotlinx.android.synthetic.main.vip_pay_iitem.view.*

class FindVipCommodityListAdapter(
    val context: Context,
    val result: List<FindVipCommodityListBeanResult>
) :
    RecyclerView.Adapter<FindVipCommodityListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.vip_pay_iitem, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return result!!.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

        if (result?.get(p1)!!.commodityName.equals("会员周卡")){
            p0.itemView.vip_img.setImageResource(R.mipmap.week_vip)
            p0.itemView.vip_text.setText("周VIP")
            if (result.get(p1).commodityName.equals("会员周卡")){
                p0.itemView.vip_bg_rl.setBackgroundResource(R.drawable.vip_bg_shape_s)
            }else{
                p0.itemView.vip_bg_rl.setBackgroundResource(R.drawable.vip_bg_shape_n)
            }
        }else if (result?.get(p1)!!.commodityName.equals("会员月卡")){
            p0.itemView.vip_img.setImageResource(R.mipmap.mouth_vip)
            p0.itemView.vip_text.setText("月VIP")
            if (result?.get(p1)!!.commodityName.equals("会员月卡")){
                p0.itemView.vip_bg_rl.setBackgroundResource(R.drawable.vip_bg_shape_s)
            }else{
                p0.itemView.vip_bg_rl.setBackgroundResource(R.drawable.vip_bg_shape_n)
            }
        }else if (result?.get(p1)!!.commodityName.equals("会员季卡")){
            p0.itemView.vip_img.setImageResource(R.mipmap.quarter_vip)
            p0.itemView.vip_text.setText("季VIP")
            if (result?.get(p1)!!.commodityName.equals("会员季卡")){
                p0.itemView.vip_bg_rl.setBackgroundResource(R.drawable.vip_bg_shape_s)
            }else{
                p0.itemView.vip_bg_rl.setBackgroundResource(R.drawable.vip_bg_shape_n)
            }
        }else{
            p0.itemView.vip_img.setImageResource(R.mipmap.year_vip)
            p0.itemView.vip_text.setText("年VIP")
            if (result?.get(p1)!!.commodityName.equals("会员年卡")){
                p0.itemView.vip_bg_rl.setBackgroundResource(R.drawable.vip_bg_shape_s)
            }else{
                p0.itemView.vip_bg_rl.setBackgroundResource(R.drawable.vip_bg_shape_n)
            }
        }

        p0.itemView.setOnClickListener {

            callBackVip?.setVip(result?.get(p1)!!.commodityName,result?.get(p1)!!.price,result.get(p1).commodityId)
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
    interface CallBackVip{
        fun setVip(vipName : String , price : Double,id:Int)
    }
    private var callBackVip : CallBackVip? = null
    fun setCallBackVip(callBackVip : CallBackVip?){
        this.callBackVip = callBackVip
    }
}