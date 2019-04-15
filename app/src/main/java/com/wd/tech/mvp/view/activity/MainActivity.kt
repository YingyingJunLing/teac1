package com.wd.tech.mvp.view.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import com.wd.tech.R
import com.wd.tech.mvp.view.frag.CommunityFragment
import com.wd.tech.mvp.view.frag.InformationFragment
import com.wd.tech.mvp.view.frag.MessageFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.ceshi.*

class MainActivity : AppCompatActivity(),View.OnClickListener {

    companion object{
        var width : Int = 0
        var height : Int = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var windowManager : WindowManager = getWindowManager()
        var metrics : DisplayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        width = metrics.widthPixels
        height = metrics.heightPixels
        ce_login.setOnClickListener(this)
        ce_reg.setOnClickListener(this)
        //初始化bottomBar
        bottomBar.init(supportFragmentManager)
            .setImgSize(70F, 70F)
            .setFontSize(10F)
            .setChangeColor(Color.BLACK,Color.GRAY)
            .setTabPadding(4F, 5F, 12F)
            .addTabItem("咨询", R.mipmap.common_tab_informatiions,R.mipmap.common_tab_information, InformationFragment::class.java)
            .addTabItem("消息", R.mipmap.common_tab_messages, R.mipmap.common_tab_message, MessageFragment::class.java)
            .addTabItem("社区", R.mipmap.common_tab_communitys,R.mipmap.common_tab_community, CommunityFragment::class.java)
        //按钮点击事件
        my_collection_next.setOnClickListener(this)
        my_attention_next.setOnClickListener(this)
        my_score_next.setOnClickListener(this)
        my_card_next.setOnClickListener(this)
        my_notice_next.setOnClickListener(this)
        my_task_next.setOnClickListener(this)
        my_setting_next.setOnClickListener(this)

        //我的页面处理
        val booleanExtra = intent.getStringExtra("booles")
        var ss:String="1"
        if (booleanExtra==ss){
            //根据用户id、查询用户信息
            object : Thread() {
                override fun run() {
                    super.run()
                    try {
                        Thread.sleep(3000)

                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }.start()
            wei_login.visibility=View.GONE
            my_content.visibility=View.VISIBLE
        }else{
            wei_login.visibility=View.VISIBLE
            my_content.visibility=View.GONE
        }
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
        }
    }
}
