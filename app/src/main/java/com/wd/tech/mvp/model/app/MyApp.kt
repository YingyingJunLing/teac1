package com.wd.tech.mvp.model.app

import android.app.Application
import android.content.Context
import android.content.IntentFilter
import cn.jpush.im.android.api.JMessageClient
import com.facebook.drawee.backends.pipeline.Fresco
import com.mob.MobSDK
import com.wd.tech.mvp.model.utils.NetworkChangeReceiverUtil
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        var intentFilter : IntentFilter = IntentFilter()
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        var networkChangeReceiverUtil : NetworkChangeReceiverUtil = NetworkChangeReceiverUtil()
        registerReceiver(networkChangeReceiverUtil,intentFilter)



        JMessageClient.setDebugMode(true)
        JMessageClient.init(this)
        MobSDK.init(this,"2ae99603e8708","47d7cae6cfff51929a3a7cc201f7f769")
    }
}