package com.wd.tech.mvp.view.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.webkit.WebSettings
import android.widget.Toast
import com.wd.tech.mvp.model.bean.*
import com.wd.tech.mvp.presenter.presenterimpl.InfoDetailPresenter
import com.wd.tech.mvp.view.adapter.DetailCommentAdapter
import com.wd.tech.mvp.view.adapter.DetalisTuiJianAdapter
import com.wd.tech.mvp.view.adapter.PlateAdapter
import com.wd.tech.mvp.view.base.BaseActivity
import com.wd.tech.mvp.view.contract.Contract
import kotlinx.android.synthetic.main.activity_details.*

import java.text.SimpleDateFormat
import java.util.*
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.AlertDialogLayout
import android.support.v7.widget.OrientationHelper
import android.util.Log
import android.view.Gravity
import android.widget.RelativeLayout
import android.widget.PopupWindow
import com.wd.tech.R
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.popup_comment.view.*
import android.view.ViewGroup
import android.widget.EditText
import com.facebook.drawee.gestures.GestureDetector
import com.wd.tech.mvp.model.utils.AlterAndAnimationUtil
import kotlinx.android.synthetic.main.detail_pay_dialog_item.view.*
import kotlinx.android.synthetic.main.pop_share.view.*


class DetailsActivity : BaseActivity<Contract.IInfoDetailView,InfoDetailPresenter>(),Contract.IInfoDetailView,
    View.OnClickListener {


    var infoDetailPresenter:InfoDetailPresenter?=null
    val FORMAT_DATE_TIME_PATTERN = "HH:mm:ss"
    var whetherPay:Int=0
    var page:Int =1
    var id :Int = 0
    var count:Int =10
    var whetherCollection :Int= 0
    var useId :String  ?= null
    var greate :Int = 0
    var session :String  ?= null
    var hashMap : HashMap<String,String> = HashMap()
    var map : HashMap<String,String> = HashMap()
    lateinit var result:InfoDetailBean.ResultBean
    lateinit var commentadapter: DetailCommentAdapter
  var alertAndAnimationUtils : AlterAndAnimationUtil?= null
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
        id = sid
        var collection = intent.getIntExtra("whetherCollection",0)
        whetherCollection = collection
        whetherPay = pay
        infoDetailPresenter?.onInfoDetailPre(hashMap,sid)
        infoDetailPresenter?.onDetailCommentPre(sid,page,count)
        //创建评论弹框
        onCreatComment()
    }

    //创建评论弹框
    fun onCreatComment() {
  val view1 = View.inflate(this, R.layout.popup_comment, null)
//        ccomment_cotent = view1.findViewById(R.id.edit_comment)
//        comment_submit = view1.findViewById(R.id.text_comment_submit)
        val  popupWindow1 =
            PopupWindow(view1, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true)
        val cdw = ColorDrawable(resources.getColor(R.color.white))
        //设置颜色
        popupWindow1.setBackgroundDrawable(cdw)
        popupWindow1.setFocusable(true)
    }

    /**
     * 初始化控件
     */
    override fun initView() {
        var sharedPreferences : SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)
        var userId = sharedPreferences.getString("userId", "0")
        var sessionId = sharedPreferences.getString("sessionId", "0")
        useId =userId
        session =sessionId
        hashMap.put("userId",userId)
        hashMap.put("sessionId",sessionId)
        alertAndAnimationUtils = AlterAndAnimationUtil()
        //返回按钮
        message_details_return.setOnClickListener(this)
        //输入框
        message_details_edit.setOnClickListener(this)
        //点赞
        message_details_like.setOnClickListener(this)
        //收藏
        message_details_attention.setOnClickListener(this)
        //分享
        message_details_share.setOnClickListener(this)
    }

    /**
     * 成功的方法
     */
    @SuppressLint("JavascriptInterface", "ResourceType")
    override fun onSuccess(any: Any) {
        //详情页面
        if(any is InfoDetailBean)
        {
            loading_linear_info.visibility = View.GONE
            message_details_scroll.visibility =View.VISIBLE
            if(any !=null)
            {
                 result = any.getResult()!!
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
                    message_details_buy.setOnClickListener(this)

                }
                message_details_message_num.text= result?.comment.toString()
                message_details_share_num.text= result?.share.toString()
                message_details_like_num.text=result?.praise.toString()
                greate = result.whetherGreat
                if(whetherCollection == 1)
                {
                    message_details_attention.setImageResource(R.mipmap.common_icon_collect_s)
                }else{

                    message_details_attention.setImageResource(R.mipmap.common_icon_collect)
                }
                if (greate==1)
                {
                    Log.e("nhj","赞"+greate)
                    message_details_like.setBackgroundResource(R.mipmap.common_icon_praise_s)
                }
                if (greate==2)
                {
                    Log.e("nhj","没有赞"+greate)
                    message_details_like.setBackgroundResource(R.mipmap.common_icon_prise_n_x)
                }
            }
        }
        //点赞
        if(any is AddGreatRecordBean)
        {
            Toast.makeText(this@DetailsActivity,any.message,Toast.LENGTH_LONG).show()
          result.praise= result.praise+1

        }
        //消取点赞
        if(any is CancelGreateBean)
        {
            result.praise= result.praise-1
            Toast.makeText(this@DetailsActivity,any.message,Toast.LENGTH_LONG).show()
        }
        //收藏
        if(any is InformationCollcetionBean)
        {
            Toast.makeText(this@DetailsActivity,any.getMessage(),Toast.LENGTH_LONG).show()
        }
        //消取收藏
        if(any is InformationCollcetionBeanNo)
        {
            Toast.makeText(this@DetailsActivity,any.getMessage(),Toast.LENGTH_LONG).show()
        }
        //添加评论
        if(any is AddInfoCommentBean)
        {
            Toast.makeText(this@DetailsActivity,any.message,Toast.LENGTH_LONG).show()
            hideInput()
        }

    }

    //收起软键盘
    protected fun hideInput() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val v = window.peekDecorView()
        if (null != v) {
            imm.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

    /**
     * 咨询评论展示
     */
    override fun onDetailCommentSuccess(any: Any)
    {
        if(any is DetailCommentBean)
        {
        Handler().postDelayed(object :Runnable{
            override fun run() {
                    loading_linear_info.visibility = View.GONE
                    message_details_scroll.visibility =View.VISIBLE
                    message_details_comment_recycler.layoutManager= LinearLayoutManager(this@DetailsActivity)
                     commentadapter = DetailCommentAdapter(this@DetailsActivity,any)
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
    override fun onClick(v: View?) {
        when(v!!.id) {
            //返回
            R.id.message_details_return -> {
                finish()
            }
            //输入框  评论
            R.id.message_details_edit -> {
                val view1 = View.inflate(this, R.layout.popup_comment, null)
                val popupWindow1 =
                    PopupWindow(
                        view1,
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        true
                    )
                val cdw = ColorDrawable(resources.getColor(R.color.white))
                //设置颜色
                popupWindow1.setBackgroundDrawable(cdw)
                popupWindow1.setFocusable(true)
                popupWindow1.showAtLocation(
                    View.inflate(this@DetailsActivity, R.layout.activity_details, null),
                    Gravity.BOTTOM, 0, 0
                )
                view1.text_comment_submit.setOnClickListener(View.OnClickListener {
                    if (useId.equals("0") || session.equals("0")) {
                        Toast.makeText(this@DetailsActivity, "你还没有登录哟，请先登录", Toast.LENGTH_LONG).show()
                        return@OnClickListener
                    } else {
                        val comment = view1.edit_comment.getText().toString()
                        infoDetailPresenter!!.onIAddInfoCommentPre(hashMap, result.id, comment)
                        popupWindow1.dismiss()
                        commentadapter.notifyDataSetChanged()
                        hideInput()
                    }

                })
            }
            //点赞
            R.id.message_details_like -> {
                if (useId.equals("0") || session.equals("0")) {
                    Toast.makeText(this@DetailsActivity, "你还没有登录哟，请先登录", Toast.LENGTH_LONG).show()
                    return
                } else {
                    if (result.whetherGreat == 1) {
                        map.put("infoId", "" + result.id)
                        infoDetailPresenter!!.onICancelGreaPre(hashMap, map)
                        message_details_like.setBackgroundResource(R.mipmap.common_icon_prise_n_x)
                        result.whetherGreat = 2
                    } else {
                        map.put("infoId", "" + result.id)
                        infoDetailPresenter!!.onIAddGreatRecordPre(hashMap, map)
                        message_details_like.setBackgroundResource(R.mipmap.common_icon_praise_s)
                        result.whetherGreat = 1

                    }
                }
            }
            //关注收藏
            R.id.message_details_attention -> {
                if (useId.equals("0") || session.equals("0")) {
                    Toast.makeText(this@DetailsActivity, "你还没有登录哟，请先登录", Toast.LENGTH_LONG).show()
                    return
                } else {
                    if (whetherCollection == 1) {
                        message_details_attention.setImageResource(R.mipmap.common_icon_collect)
                        infoDetailPresenter!!.onICancelCollectionPre(hashMap, result.id)
                        whetherCollection = 2
                    } else {
                        infoDetailPresenter!!.onIAddCollectionPre(hashMap, result.id)
                        message_details_attention.setImageResource(R.mipmap.common_icon_collect_s)
                        whetherCollection = 1
                    }
                }
            }
            //分享
            R.id.message_details_share -> {
                val view = View.inflate(this, R.layout.pop_share, null)
//                share_fc = view.findViewById<View>(R.id.share_friendcricle_image)
//                share_f = view.findViewById<View>(R.id.share_friend_image)
                val popupWindow =
                    PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true)
                val cdw = ColorDrawable(resources.getColor(R.color.text_color))
                //设置颜色
                popupWindow.setBackgroundDrawable(cdw)
                popupWindow.setFocusable(true)
                popupWindow.showAsDropDown(view, 1, -1)
                //点击取消
                view.share_cancel.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        popupWindow.dismiss()
                    }
                })
                //微信好友分享
                view.share_friend_image.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        Toast.makeText(this@DetailsActivity, "微信分享", Toast.LENGTH_LONG).show()
                    }
                })
                //微信朋友圈分享
                view.share_friendcricle_image.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        Toast.makeText(this@DetailsActivity, "朋友圈分享", Toast.LENGTH_LONG).show()
                    }
                })
            }
            //点击付费弹框
            R.id.message_details_buy -> {
                val view = View.inflate(this@DetailsActivity, R.layout.detail_pay_dialog_item, null)
                alertAndAnimationUtils!!.AlterDialog(this@DetailsActivity, view)
                //点击隐藏
                view.dialog_dismiss_ibt.setOnClickListener(View.OnClickListener
                { alertAndAnimationUtils!!.hideDialog()
                })
                //点击积分兑换
                view.detail_pay_duihuan.setOnClickListener(object :View.OnClickListener{
                    override fun onClick(v: View?) {

                    }
                })
                //点击开通会员
                view.detail_pay_kaitong.setOnClickListener(object :View.OnClickListener{
                    override fun onClick(v: View?) {

                    }
                })

            }
        }
    }
}


