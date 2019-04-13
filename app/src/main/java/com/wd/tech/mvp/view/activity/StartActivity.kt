package com.wd.tech.mvp.view.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import com.wd.tech.R

class StartActivity : AppCompatActivity() {

    private var handler : Handler = object : Handler(){
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            sendEmptyMessageDelayed(1,1000)
            jumpActivity()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        handler.sendEmptyMessageDelayed(1,1500)
    }

    private fun jumpActivity(){
        var intent : Intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
        handler.removeMessages(1)
    }
}
