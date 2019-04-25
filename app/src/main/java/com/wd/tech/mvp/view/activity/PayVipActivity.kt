package com.wd.tech.mvp.view.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import com.alipay.sdk.app.PayTask
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.wd.tech.R
import com.wd.tech.md5.Md5
import com.wd.tech.mvp.model.bean.ByVipBean
import com.wd.tech.mvp.model.bean.FindVipCommodityListBean
import com.wd.tech.mvp.model.bean.WechatPayBean
import com.wd.tech.mvp.model.bean.ZhiFuBaoBean
import com.wd.tech.mvp.model.utils.RetrofitUtil
import com.wd.tech.mvp.presenter.presenterimpl.VipPresenter
import com.wd.tech.mvp.view.adapter.FindVipCommodityListAdapter
import com.wd.tech.mvp.view.base.BaseActivity
import com.wd.tech.mvp.view.contract.Contract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_pay_vip.*
import java.util.HashMap

class PayVipActivity : BaseActivity<Contract.IVipView, VipPresenter>() ,Contract.IVipView, View.OnClickListener {


    var vipPresenter:VipPresenter?=null
    var cid :Int = 0
    var type :Int = 0
    var orderByID:String?=null
    var useId:String ?=null
    var hashMap: HashMap<String, String> = HashMap()
    override fun createPresenter(): VipPresenter? {
     vipPresenter = VipPresenter(this)
        return vipPresenter
    }

    override fun initActivityView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_pay_vip)
    }

    override fun initData() {
        vipPresenter!!.onIFindVipCommodityListPre()

    }

    override fun initView() {

        var sharedPreferences: SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)
        var userId = sharedPreferences.getString("userId","0")
        var sessionId = sharedPreferences.getString("sessionId", "0")
        hashMap.put("userId", userId)
        hashMap.put("sessionId", sessionId)
        useId = userId
        vip_rec.layoutManager=GridLayoutManager(this@PayVipActivity,2)
        pay_vip_back.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                finish()
            }
        })
        //点击立即开通
        immediately_oppened_bt.setOnClickListener(this)
        pay_type_1.setOnCheckedChangeListener(object :CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
               type  =1
            }
        })
        pay_type_2.setOnCheckedChangeListener(object :CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                type = 2
            }
        })
    }

    override fun onSuccess(any: Any) {
        if(any is FindVipCommodityListBean)
        {
            val result = any.result
            var adapter = FindVipCommodityListAdapter(this,result)
            adapter.setCallBackVip(object :FindVipCommodityListAdapter.CallBackVip{

                override fun setVip(vipName: String, price: Double, id:Int) {
                    vip_money.text = price.toString()
                    cid = id
                }
            })
            vip_rec.adapter = adapter
        }
        if(any is ByVipBean)
        {
           orderByID =  any.orderId
        }
    }

    override fun onFail() {

    }

    /**
     * 点击事件
     */
    override fun onClick(v: View?) {
        when(v!!.id)
        {
            //立即开通
            R.id.immediately_oppened_bt->
            {
                var signn =  Md5.MD5(useId+cid+"tech")
                vipPresenter?.onIByVip(hashMap,cid, signn)
                //微信支付
                if(type ==1)
                {
                    val sslRetrofit = RetrofitUtil.instant.SSLRetrofit()
                    sslRetrofit.getWechatPay(hashMap, orderByID,1)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : DisposableObserver<WechatPayBean>() {
                            override fun onNext(t: WechatPayBean) {

                                val api = WXAPIFactory.createWXAPI(this@PayVipActivity, null)
                                api.registerApp("wx4c96b6b8da494224")
                                val req = PayReq()
                                req.appId = t.appId//你的微信appid
                                req.partnerId = t.partnerId//商户号
                                req.prepayId = t.prepayId//预支付交易会话ID
                                req.nonceStr = t.nonceStr//随机字符串
                                req.timeStamp = t.timeStamp//时间戳
                                req.packageValue = t.packageValue//扩展字段,这里固定填写Sign=WXPay
                                req.sign = t.sign//签名
                                // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                                api.sendReq(req)
                            }

                            override fun onError(e: Throwable) {

                            }

                            override fun onComplete() {

                            }
                        })
                }else if(type ==2)
                    //支付宝支付
                {
                    val sslRetrofit = RetrofitUtil.instant.SSLRetrofit()
                    sslRetrofit.getZhiPay(hashMap,orderByID,2)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : DisposableObserver<ZhiFuBaoBean>() {
                            override fun onComplete() {

                            }

                            override fun onNext(t: ZhiFuBaoBean) {
                                val result = t.result // 订单信息
                                val payRunnable = Runnable {
                                    val myRecordActivity = this@PayVipActivity
                                    val alipay = PayTask(myRecordActivity)
                                    alipay.payV2(result, true)
                                }
                                // 必须异步调用
                                val payThread = Thread(payRunnable)
                                payThread.start()
                            }

                            override fun onError(e: Throwable) {

                            }
                        })


                }else{
                    Toast.makeText(this@PayVipActivity,"请选择支付方式",Toast.LENGTH_LONG).show()
                }

            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        if(vipPresenter!=null)
        {
            vipPresenter!!.detachView()
        }
    }
}
