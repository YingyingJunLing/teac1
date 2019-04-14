package com.wd.tech.mvp.model.utils

import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.wd.tech.R

class NetworkChangeReceiverUtil : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        var connectionManager : ConnectivityManager = context!!.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        var networkInfo : NetworkInfo ?= connectionManager.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected == true){
            when(networkInfo.subtype){

            }
        }else{
            var view : View = LayoutInflater.from(context).inflate(R.layout.network_change_dialog,null)
            var bulider : AlertDialog.Builder = AlertDialog.Builder(context)
            val alertDialog : AlertDialog = bulider.create()
            alertDialog.window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT)
            alertDialog.setView(view)
            alertDialog.show()
            Toast.makeText(context, "当前无网络连接", Toast.LENGTH_SHORT).show()
        }
    }
}