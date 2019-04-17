package com.wd.tech.mvp.view.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.UserInfoBean
import com.wd.tech.mvp.model.bean.UserSignBean
import com.wd.tech.mvp.model.utils.FrescoUtil
import com.wd.tech.mvp.presenter.presenterimpl.UserInfoPresenter
import com.wd.tech.mvp.view.base.BaseActivity
import com.wd.tech.mvp.view.contract.Contract
import com.wd.tech.mvp.view.frag.CommunityFragment
import com.wd.tech.mvp.view.frag.InformationFragment
import com.wd.tech.mvp.view.frag.MessageFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.ceshi.*

class MainActivity : BaseActivity<Contract.IUserInfoView,UserInfoPresenter>(),Contract.IUserInfoView,View.OnClickListener {

    companion object{
        var width : Int = 0
        var height : Int = 0
    }
    var userInfoPresenter : UserInfoPresenter = UserInfoPresenter(this,null)
    var hashMap : HashMap<String,String> = HashMap()

    override fun createPresenter(): UserInfoPresenter? {
        return userInfoPresenter
    }

    override fun initActivityView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
        var windowManager : WindowManager = getWindowManager()
        var metrics : DisplayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        width = metrics.widthPixels
        height = metrics.heightPixels
    }

    override fun initData() {
        var sharedPreferences : SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)
        var userId = sharedPreferences.getString("userId", "0")
        var sessionId = sharedPreferences.getString("sessionId", "0")
        //我的页面处理
        if (userId != "0" && sessionId != "0"){
            wei_login.visibility=View.GONE
            my_content.visibility=View.VISIBLE
            hashMap.put("userId",userId)
            hashMap.put("sessionId",sessionId)
            userInfoPresenter.onIUserInfoPre(hashMap)
        }else{
            wei_login.visibility=View.VISIBLE
            my_content.visibility=View.GONE
        }
    }

    override fun initView() {
        drawer_layout.addDrawerListener(object : DrawerLayout.DrawerListener{
            override fun onDrawerStateChanged(p0: Int) {
                
            }

            override fun onDrawerSlide(p0: View, p1: Float) {

            }

            override fun onDrawerClosed(p0: View) {

            }

            override fun onDrawerOpened(p0: View) {
                p0.isClickable = true
            }

        })
        ce_login.setOnClickListener(this)
        ce_reg.setOnClickListener(this)
        //初始化bottomBar
        bottomBar.init(supportFragmentManager)
            .setImgSize(60F, 60F)
            .setFontSize(10F)
            .setChangeColor(Color.BLACK,Color.GRAY)
            .setTabPadding(4F, 5F, 12F)
            .addTabItem("咨询", R.mipmap.common_tab_informatiions,R.mipmap.common_tab_information, InformationFragment::class.java)
            .addTabItem("消息", R.mipmap.common_tab_messages, R.mipmap.common_tab_message, MessageFragment::class.java)
            .addTabItem("社区", R.mipmap.common_tab_communitys,R.mipmap.common_tab_community, CommunityFragment::class.java)
        user_head_constrain.maxHeight = height/4+100
        user_constraintLayout_show.maxHeight = (height/4*3)
    }

    override fun onSuccess(userInfoBean: UserInfoBean) {
        if (userInfoBean.status == "0000"){
            FrescoUtil.setPic(userInfoBean.result.headPic,my_header)
            my_nickName.setText(userInfoBean.result.nickName)
            my_qianming.setText(userInfoBean.result.signature)
            //按钮点击事件
            my_collection_next.setOnClickListener(this)
            my_attention_next.setOnClickListener(this)
            my_score_next.setOnClickListener(this)
            my_card_next.setOnClickListener(this)
            my_notice_next.setOnClickListener(this)
            my_task_next.setOnClickListener(this)
            my_setting_next.setOnClickListener(this)
            my_qiandao.setOnClickListener(this)
        }else{
            wei_login.visibility=View.VISIBLE
            my_content.visibility=View.GONE
        }
    }

    override fun onSignSuccess(userSignBean: UserSignBean) {
        Toast.makeText(this@MainActivity,userSignBean.message,Toast.LENGTH_LONG).show()
    }

    override fun onFail() {

    }

    private fun setAndroidNativeLightStatusBar(activity: MainActivity, dark: Boolean) {
        val decor = activity.window.decorView
        if (dark) {
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
    }
    /**
     * 按钮点击事件
     */
    override fun onClick(v: View?) {
        when(v!!.id) {
            //登录
            R.id.ce_login -> {
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
            }
            //注册
            R.id.ce_reg -> {
                startActivity(Intent(this@MainActivity, RegisterActivity::class.java))
            }
            R.id.my_collection_next->{
                startActivity(Intent(this@MainActivity, MyCollectionActivity::class.java))
            }
            R.id.my_attention_next->{
                startActivity(Intent(this@MainActivity, MyAttentionActivity::class.java))
            }
            R.id.my_score_next->{
                startActivity(Intent(this@MainActivity, MyScoreActivity::class.java))
            }
            R.id.my_card_next->{
                startActivity(Intent(this@MainActivity, MyCardActivity::class.java))
            }
            R.id.my_notice_next->{
                startActivity(Intent(this@MainActivity, MyNoticeActivity::class.java))
            }
            R.id.my_task_next->{
                startActivity(Intent(this@MainActivity, MyTaskActivity::class.java))
            }
            R.id.my_setting_next->{
                startActivity(Intent(this@MainActivity, MySettingActivity::class.java))
            }
            R.id.my_qiandao->{
                userInfoPresenter.onIUserSignPre(hashMap)
            }
        }
    }
}
