package com.wd.tech.mvp.view.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.wd.tech.R
import com.wd.tech.mvp.view.frag.CommunityFragment
import com.wd.tech.mvp.view.frag.InformationFragment
import com.wd.tech.mvp.view.frag.MessageFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setAndroidNativeLightStatusBar(MainActivity@this, true)
        ce_login.setOnClickListener(this)
        ce_reg.setOnClickListener(this)
        //初始化bottomBar
        bottomBar.init(supportFragmentManager)
            .setImgSize(50F, 50F)
            .setFontSize(10F)
            .setChangeColor(Color.BLACK,Color.GRAY)
            .setTabPadding(4F, 5F, 12F)
            .addTabItem("咨询", R.mipmap.common_tab_informatiions,R.mipmap.common_tab_information, InformationFragment::class.java)
            .addTabItem("消息", R.mipmap.common_tab_messages, R.mipmap.common_tab_message, MessageFragment::class.java)
            .addTabItem("社区", R.mipmap.common_tab_communitys,R.mipmap.common_tab_community, CommunityFragment::class.java)

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
                Toast.makeText(this,"点击了登录",Toast.LENGTH_LONG).show()
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
            }
            //注册
            R.id.ce_reg -> {
                Toast.makeText(this,"点击了注册",Toast.LENGTH_LONG).show()
                startActivity(Intent(this@MainActivity, RegisterActivity::class.java))
            }
        }
    }


}
