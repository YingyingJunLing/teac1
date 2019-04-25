package com.wd.tech.mvp.view.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.facebook.drawee.gestures.GestureDetector
import com.wd.tech.R
import com.wd.tech.mvp.presenter.presenterimpl.MyCardPresenter
import com.wd.tech.mvp.view.adapter.MyCardAdapter
import com.wd.tech.mvp.view.base.BaseActivity
import com.wd.tech.mvp.view.contract.Contract
import kotlinx.android.synthetic.main.activity_my_card.*
import kotlinx.android.synthetic.main.my_card_item.*
import java.util.HashMap

class MyCardActivity : BaseActivity<Contract.IMyCardView,MyCardPresenter>(),Contract.IMyCardView {
    var myCardPresenter:MyCardPresenter?=null
    var hashMap: HashMap<String, String> = HashMap()
    var page :Int = 1
    var count :Int =10
    override fun createPresenter(): MyCardPresenter? {
        myCardPresenter = MyCardPresenter(this)
        return myCardPresenter
    }

    override fun initActivityView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_my_card)
    }

    override fun initData() {
         myCardPresenter!!.onIMyCardPre(hashMap,page,count)
    }

    override fun initView() {
        my_card_recy.layoutManager = LinearLayoutManager(this@MyCardActivity,LinearLayoutManager.VERTICAL,false)
        var sharedPreferences: SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)
        var userId = sharedPreferences.getString("userId", "0")
        var sessionId = sharedPreferences.getString("sessionId", "0")
        hashMap.put("userId", userId)
        hashMap.put("sessionId", sessionId)
        my_card_back.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                finish()
            }
        })
        //删除的方法

    }

    override fun onSuccess(any: Any) {
        if(any is MyCardActivityBean)
        {
            val result = any.getResult()
            my_card_recy.adapter= MyCardAdapter(this,result)
        }
    }

    override fun onFail() {

    }

}
