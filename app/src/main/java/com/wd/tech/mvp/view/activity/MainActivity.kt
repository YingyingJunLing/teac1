package com.wd.tech.mvp.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.wd.tech.R
import com.wd.tech.mvp.model.api.ApiServer
import com.wd.tech.mvp.model.utils.RetrofitUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var apiServer : ApiServer = RetrofitUtil.instant.SSLRetrofit()
        apiServer.getBannerShow()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(Consumer {
                Log.i("轮播图",it.result.get(0).title)
            })
    }
}
