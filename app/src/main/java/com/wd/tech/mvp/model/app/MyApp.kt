package com.wd.tech.mvp.model.app

import android.app.Application
import android.content.IntentFilter
import com.facebook.drawee.backends.pipeline.Fresco
import com.wd.tech.mvp.model.utils.NetworkChangeReceiverUtil

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        var intentFilter : IntentFilter = IntentFilter()
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        var networkChangeReceiverUtil : NetworkChangeReceiverUtil = NetworkChangeReceiverUtil()
        registerReceiver(networkChangeReceiverUtil,intentFilter)
    }
}