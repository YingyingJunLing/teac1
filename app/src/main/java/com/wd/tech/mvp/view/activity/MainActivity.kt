package com.wd.tech.mvp.view.activity

import android.arch.lifecycle.LiveData
import android.graphics.Color
import android.os.Bundle
import android.os.Message
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import com.wd.tech.R
import com.wd.tech.mvp.view.frag.CommunityFragment
import com.wd.tech.mvp.view.frag.InformationFragment
import com.wd.tech.mvp.view.frag.MessageFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout,toolbar , R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()



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





}
