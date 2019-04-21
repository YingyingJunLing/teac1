package com.wd.tech.mvp.view.activity


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.view.View
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.InfoRecommendListBean

import com.wd.tech.mvp.presenter.presenterimpl.SearchAllPlatePresenter
import com.wd.tech.mvp.view.adapter.SearchAllPlateAdapter
import com.wd.tech.mvp.view.base.BaseActivity
import com.wd.tech.mvp.view.contract.Contract
import kotlinx.android.synthetic.main.activity_search_all_plate.*

class SearchAllPlateActivity : BaseActivity<Contract.IInfoSelectPlateView, SearchAllPlatePresenter>(), Contract.IInfoSelectPlateView, View.OnClickListener {
    var searchAllPlatePresenter: SearchAllPlatePresenter?=null
    var sid :Int = 0
    var page :Int =1
    var count :Int =10
    val hashMap = HashMap<String,String>()
    override fun createPresenter(): SearchAllPlatePresenter? {
        searchAllPlatePresenter =SearchAllPlatePresenter(this)
        return searchAllPlatePresenter
    }

    override fun initActivityView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_search_all_plate)
        var sharedPreferences : SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)
        var userId = sharedPreferences.getString("userId", "0")
        var sessionId = sharedPreferences.getString("sessionId", "0")
        hashMap.put("userId",userId)
        hashMap.put("sessionId",sessionId)
    }

    override fun initData() {
       searchAllPlatePresenter!!.onfindAllInfoPlateByID(hashMap,sid,page,count)
    }

    override fun initView() {
        //得到intent传的值
        var intent = intent
        var name =  intent.getStringExtra("name")
        val id = intent.getIntExtra("id", 0)
        sid  = id
        search_edit.text= name
        search_all_plate_recy.layoutManager = LinearLayoutManager(this,OrientationHelper.VERTICAL,false)
        text_finsh.setOnClickListener(this)
    }

    override fun onSuccess(any: Any) {
        if(any is InfoRecommendListBean)
        {
            var adapter = SearchAllPlateAdapter(this,any)
            search_all_plate_recy.adapter = adapter
        }

    }

    override fun onFail() {

    }

    override fun onClick(v: View?) {
        when(v!!.id)
        {
            R.id.text_finsh->
            {
                finish()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        if(searchAllPlatePresenter!=null)
        {
            searchAllPlatePresenter!!.detachView()
        }
    }
}
