package com.wd.tech.mvp.view.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.MyScoreBean
import com.wd.tech.mvp.presenter.presenterimpl.MyScorePresenter
import com.wd.tech.mvp.view.base.BaseActivity
import com.wd.tech.mvp.view.contract.Contract
import kotlinx.android.synthetic.main.activity_my_score.*

class MyScoreActivity : BaseActivity<Contract.IMyScoreView,MyScorePresenter>(),Contract.IMyScoreView {
    var myScorePresenter:MyScorePresenter?=null
    var hashMap : HashMap<String,String> = HashMap()
    override fun createPresenter(): MyScorePresenter? {
       myScorePresenter = MyScorePresenter(this)
        return myScorePresenter
    }

    override fun initActivityView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_my_score)
    }

    override fun initData() {
        myScorePresenter!!.onIMyScorePre(hashMap)
    }

    override fun initView() {
        var sharedPreferences : SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)
        var userId = sharedPreferences.getString("userId", "0")
        var sessionId = sharedPreferences.getString("sessionId", "0")
        hashMap.put("userId",userId)
        hashMap.put("sessionId",sessionId)

    }

    override fun onSuccess(any: Any) {
        if(any is MyScoreBean)
        {
            text_amount_integral.text = any.result.integral.toString()
        }

    }

    override fun onFail() {

    }

}
