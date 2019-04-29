package com.wd.tech.mvp.view.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.view.View
import android.widget.Toast
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.MyNoticeBean
import com.wd.tech.mvp.presenter.presenterimpl.MyNoticePresenter
import com.wd.tech.mvp.view.adapter.MyNoticeAdapter
import com.wd.tech.mvp.view.base.BaseActivity
import com.wd.tech.mvp.view.contract.Contract
import kotlinx.android.synthetic.main.activity_my_notice.*

class MyNoticeActivity : BaseActivity<Contract.IMyNoticeView,MyNoticePresenter>(),Contract.IMyNoticeView,
    View.OnClickListener {
    var hashMap : HashMap<String,String> = HashMap()
    override fun onClick(v: View?) {
        when(v!!.id)
        {
            R.id.my_notice_back->
            {
                finish()
            }
        }
    }

    var myNoticePresenter:MyNoticePresenter?=null
    override fun createPresenter(): MyNoticePresenter? {
        myNoticePresenter = MyNoticePresenter(this)
        return myNoticePresenter

    }

    override fun initActivityView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_my_notice)
    }

    override fun initData() {
        var sharedPreferences : SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)
        var userId = sharedPreferences.getString("userId", "0")
        var sessionId = sharedPreferences.getString("sessionId", "0")
        hashMap.put("userId",userId)
        hashMap.put("sessionId",sessionId)
        myNoticePresenter!!.onIMyNoticePre(hashMap,1,5)

    }

    override fun initView() {
        my_notice_back.setOnClickListener(this)
        my_notice_recy.layoutManager = LinearLayoutManager(this@MyNoticeActivity,OrientationHelper.VERTICAL,false)

    }

    override fun onSuccess(any: Any) {
        if(any is MyNoticeBean)
        {
            Toast.makeText(this@MyNoticeActivity,any.message,Toast.LENGTH_LONG).show()
          var adapter =    MyNoticeAdapter(this,any)
            my_notice_recy.adapter = adapter
        }

    }

    override fun onFail() {

    }

}
