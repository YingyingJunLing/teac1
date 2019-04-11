package com.wd.tech.mvp.model.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast

class NetworkChangeReceiverUtil : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        var connectionManager : ConnectivityManager = context!!.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        var networkInfo : NetworkInfo ?= connectionManager.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected == true){
            when(networkInfo.subtype){
                0 ->
                    Toast.makeText(context, "正在使用移动网络", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(context, "当前无网络连接", Toast.LENGTH_SHORT).show()
        }
    }
}