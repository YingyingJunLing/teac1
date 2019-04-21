package com.wd.tech.mvp.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import com.wd.tech.R

import kotlinx.android.synthetic.main.activity_advertisement_detail.*
import kotlinx.android.synthetic.main.activity_details.*


class AdvertisementDetailActivity : AppCompatActivity() {

    private var url: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.wd.tech.R.layout.activity_advertisement_detail)
        inform_sdg.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                startActivity(Intent(this@AdvertisementDetailActivity,MainActivity::class.java))
            }
        })
        initData()
    }

    @SuppressLint("JavascriptInterface")
    private fun initData() {
        //得到intent跳转带来的内容
        val intent = intent
        url = intent.getStringExtra("url")
        Log.e("url",url)
        //webView
        val webSettings = adver_detail_webview.getSettings()
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
        adver_detail_webview.loadUrl(url)

        webSettings.useWideViewPort = true
        val maxsp = resources.getDimension(R.dimen.text_size1)
        val t = maxsp.toInt()
        webSettings.defaultFontSize = t//设置 WebView 字体的大小，默认大小为 16
        adver_detail_webview.getSettings().setJavaScriptEnabled(true)
        // 设置可以支持缩放
        adver_detail_webview.getSettings().setSupportZoom(true)
        // 设置出现缩放工具
        adver_detail_webview.getSettings().setBuiltInZoomControls(true)
        //扩大比例的缩放
        adver_detail_webview.getSettings().setUseWideViewPort(true)

    }
}
