package com.wd.tech.mvp.view.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.InfoDetailBean
import com.wd.tech.mvp.presenter.presenterimpl.InfoDetailPresenter
import com.wd.tech.mvp.view.base.BaseActivity
import com.wd.tech.mvp.view.contract.Contract
import kotlinx.android.synthetic.main.activity_details.*
import java.text.SimpleDateFormat
import java.util.*

class DetailsActivity : BaseActivity<Contract.IInfoDetailView,InfoDetailPresenter>(),Contract.IInfoDetailView {
    var infoDetailPresenter:InfoDetailPresenter?=null
    val FORMAT_DATE_TIME_PATTERN = "HH:mm:ss"
    var whetherPay:Int=0
    override fun createPresenter(): InfoDetailPresenter? {
        infoDetailPresenter = InfoDetailPresenter(this)
        return infoDetailPresenter
    }

    override fun initActivityView(savedInstanceState: Bundle?)
    {
        setContentView(R.layout.activity_details)
    }

    override fun initData() {
        val intent = intent
        var sid =  intent.getIntExtra("id",0)
        var pay =   intent.getIntExtra("whetherPay",0)
        whetherPay = pay
        Toast.makeText(this,sid.toString(),Toast.LENGTH_LONG).show()
        infoDetailPresenter?.onInfoDetailPre(sid)
    }

    override fun initView() {

    }

    override fun onSuccess(any: Any) {
        if(any is InfoDetailBean)
        {
            if(any !=null)
            {
                val result = any.getResult()
                detail_title.text= result?.title
                var dateFormat= SimpleDateFormat(FORMAT_DATE_TIME_PATTERN, Locale.getDefault())
                detail_time.setText(dateFormat.format(any.getResult()?.releaseTime))
                detail_source.text=result?.source
                if(whetherPay == 2)
                {

                    par_webview.loadDataWithBaseURL(null, result?.content, "text/html", "utf-8", null)

                }else{
                    pay.visibility = View.VISIBLE
                    detali_pay.visibility =View.VISIBLE
                   par_webview.visibility =View.GONE
                    tuijian.visibility =View.GONE
                }

                detail_message_num.text= result?.comment.toString()
                detail_share_num.text= result?.share.toString()
                detail_zan_num.text=result?.praise.toString()
            }
        }
    }

    override fun onFail() {

    }

    override fun onDestroy() {
        super.onDestroy()
        if(infoDetailPresenter!=null)
        {
            infoDetailPresenter!!.detachView()
        }
    }

}


