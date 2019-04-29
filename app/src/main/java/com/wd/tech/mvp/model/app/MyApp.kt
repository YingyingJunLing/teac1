package com.wd.tech.mvp.model.app

import android.app.Application
import android.content.Context
import android.content.IntentFilter
import cn.jpush.im.android.api.JMessageClient
import com.example.arclibrary.builder.AcrFaceManagerBuilder
import com.facebook.drawee.backends.pipeline.Fresco
import com.mob.MobSDK

import com.wd.tech.mvp.model.utils.NetworkChangeReceiverUtil
import com.yorhp.picturepick.PicturePickUtil

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
        initArcFace()
        PicturePickUtil.init("com.yorhp.arcface.fileProvider")
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }

    private fun initArcFace() {

        AcrFaceManagerBuilder().setContext(this)
            .setFreeSdkAppId(Constants.FREESDKAPPID)
            .setFdSdkKey(Constants.FDSDKKEY)
            .setFtSdkKey(Constants.FTSDKKEY)
            .setFrSdkKey(Constants.FRSDKKEY)
            .setLivenessAppId(Constants.LIVENESSAPPID)
            .setLivenessSdkKey(Constants.LIVENESSSDKKEY)
            .create()
    }
}