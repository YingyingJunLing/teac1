package com.wd.tech.mvp.view.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View

import android.widget.Toast
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.DeletePostBean


import com.wd.tech.mvp.model.bean.MyCardActivityBean

import com.wd.tech.mvp.presenter.presenterimpl.MyCardPresenter
import com.wd.tech.mvp.view.adapter.MyCardAdapter
import com.wd.tech.mvp.view.base.BaseActivity
import com.wd.tech.mvp.view.contract.Contract
import kotlinx.android.synthetic.main.activity_my_card.*

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

import java.util.HashMap

class MyCardActivity : BaseActivity<Contract.IMyCardView,MyCardPresenter>(),Contract.IMyCardView {
    var myCardPresenter:MyCardPresenter?=null
    var hashMap: HashMap<String, String> = HashMap()
    var page :Int = 1
    var count :Int =10
    var cid :String ? =null
    var adapter :MyCardAdapter?=null
    override fun createPresenter(): MyCardPresenter? {
        myCardPresenter = MyCardPresenter(this)
        return myCardPresenter
    }

    override fun initActivityView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_my_card)
        EventBus.getDefault().register(this)
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
    }
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    fun getEventBus(id:String)
    {
        cid = id
    }

    override fun onSuccess(any: Any) {
        if(any is MyCardActivityBean)
        {
            val result = any.getResult()
            my_card_recy. adapter= MyCardAdapter(this,result)
            adapter = MyCardAdapter(this,result)
            adapter?.setCallBackDelete(object :MyCardAdapter.CallBackDelelte{
                override fun setDelete() {

                  myCardPresenter?.onIDeleltePost(hashMap, cid.toString())
                    adapter?.notifyDataSetChanged()
                }
            })

        }
        if(any is DeletePostBean)
        {
            Toast.makeText(this@MyCardActivity,any.message,Toast.LENGTH_LONG).show()
        }
    }

    override fun onFail() {

    }

    override fun onDestroy() {
        super.onDestroy()
        if(myCardPresenter !=null)
        {
            myCardPresenter!!.detachView()
        }
        EventBus.getDefault().unregister(this)
    }

}
