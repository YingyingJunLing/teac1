package com.wd.tech.mvp.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.webkit.WebSettings
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.DetailCommentBean
import com.wd.tech.mvp.model.bean.InfoDetailBean
import com.wd.tech.mvp.presenter.presenterimpl.InfoDetailPresenter
import com.wd.tech.mvp.view.adapter.DetailCommentAdapter
import com.wd.tech.mvp.view.adapter.DetalisTuiJianAdapter
import com.wd.tech.mvp.view.adapter.PlateAdapter
import com.wd.tech.mvp.view.base.BaseActivity
import com.wd.tech.mvp.view.contract.Contract
import kotlinx.android.synthetic.main.activity_details.*
import java.text.SimpleDateFormat
import java.util.*

class DetailsActivity : BaseActivity<Contract.IInfoDetailView,InfoDetailPresenter>(),Contract.IInfoDetailView {
    var infoDetailPresenter:InfoDetailPresenter?=null
    val FORMAT_DATE_TIME_PATTERN = "HH:mm:ss"
    var whetherPay:Int=0
   var page:Int =1
    var count:Int =10
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
        infoDetailPresenter?.onInfoDetailPre(sid)
        infoDetailPresenter?.onDetailCommentPre(sid,page,count)
    }

    override fun initView() {

    }

    @SuppressLint("JavascriptInterface", "ResourceType")
    override fun onSuccess(any: Any) {
        if(any is InfoDetailBean)
        {
            loading_linear_info.visibility = View.GONE
            message_details_scroll.visibility =View.VISIBLE
            if(any !=null)
            {
                val result = any.getResult()
                //标题
                message_details_title.text=any.result!!.title
                //时间
                var dateFormat= SimpleDateFormat(FORMAT_DATE_TIME_PATTERN, Locale.getDefault())
                message_details_date.setText(dateFormat.format(any.getResult()?.releaseTime))
                //来源
                message_details_source.text=any.result!!.source
                if(whetherPay == 2)
                {
                    //webView
                    val webSettings = message_details_webview.getSettings()
                    //支持js，如果不设置本行，html中的js代码都会失效
                    webSettings.setJavaScriptEnabled(true)
                    //提高渲染的优先级
                    webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH)
                    //将图片调整到适合webview的大小
                    webSettings.setUseWideViewPort(true)
                    //缩放至屏幕大小
                    webSettings.setLoadWithOverviewMode(true)
                    //支持自动加载图片
                    webSettings.setLoadsImagesAutomatically(true)
                    //设置编码格式
                    webSettings.setDefaultTextEncodingName("utf-8")
                    //支持内容重新布局
                    webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN)
                    message_details_webview.loadDataWithBaseURL(null,any.result!!.content,"text/html", "utf-8", null)
                    message_details_webview.addJavascriptInterface(this,"android")
                    webSettings.useWideViewPort = true
                    val maxsp = resources.getDimension(R.dimen.text_size1)
                    val t = maxsp.toInt()
                    webSettings.defaultFontSize = t//设置 WebView 字体的大小，默认大小为 16
                    //推荐页面
                    //推荐的内容
                    message_details_recycler.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
                    if (any.result!!.informationList!=null)
                    {
                        Handler().postDelayed(object :Runnable{
                            override fun run() {
                                var detalisadapter = DetalisTuiJianAdapter(this@DetailsActivity,any)
                                message_details_recycler!!.adapter=detalisadapter
                            }
                        },2000)
                    }
                    //标签
                    //标签
                    message_details_plate_recycler.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
                    if (any.result!!.content != null)
                    {
                        Handler().postDelayed(object :Runnable{
                            override fun run() {
                                var plateadapter = PlateAdapter(this@DetailsActivity,any)
                                message_details_plate_recycler!!.adapter=plateadapter
                            }
                        },2000)

                    }
                }else{
                    //如果需要付费，就显示付费页面
                    free_of_charge_linear.visibility = View.VISIBLE
                    message_details_webview.visibility =View.GONE
                    recommens_linear.visibility =View.GONE
                }
                message_details_message_num.text= result?.comment.toString()
                message_details_share_num.text= result?.share.toString()
                message_details_like_num.text=result?.praise.toString()
            }
        }
    }
    override fun onDetailCommentSuccess(any: Any)
    {
        if(any is DetailCommentBean)
        {
        Handler().postDelayed(object :Runnable{
            override fun run() {
                    loading_linear_info.visibility = View.GONE
                    message_details_scroll.visibility =View.VISIBLE
                    message_details_comment_recycler.layoutManager= LinearLayoutManager(this@DetailsActivity)
                    var commentadapter = DetailCommentAdapter(this@DetailsActivity,any)
                    message_details_comment_recycler!!.adapter=commentadapter
            }
        },2000)
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


