package com.wd.tech.mvp.view.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.InfoCollectionBean
import com.wd.tech.mvp.presenter.presenterimpl.InfoCollectionPre
import com.wd.tech.mvp.view.adapter.CollectionListAdapter
import com.wd.tech.mvp.view.base.BaseActivity
import com.wd.tech.mvp.view.contract.Contract
import kotlinx.android.synthetic.main.activity_my_collection.*

class MyCollectionActivity : BaseActivity<Contract.IInfoCollectionView,InfoCollectionPre>(),Contract.IInfoCollectionView {

    var infoCollectionPre : InfoCollectionPre = InfoCollectionPre(this)
    var hashMap : HashMap<String,String> = HashMap()
    lateinit var type : String

    override fun initActivityView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_my_collection)
    }

    override fun createPresenter(): InfoCollectionPre? {
        return infoCollectionPre
    }

    override fun initData() {
        var sharedPreferences : SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)
        var userId = sharedPreferences.getString("userId", "0")
        var sessionId = sharedPreferences.getString("sessionId", "0")
        hashMap.put("userId",userId)
        hashMap.put("sessionId",sessionId)
        infoCollectionPre.onIInfoCollectionPre(hashMap,1,5)
    }

    override fun initView() {
        my_collection_rcycle.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        my_collection_del.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                collection_del.visibility = View.VISIBLE
                my_collection_del.visibility = View.GONE
                type = "1"

            }
        })
    }

    override fun onInfoCollectionSuccess(infoCollectionBean: InfoCollectionBean) {
        loading_linear.visibility = View.GONE
        val list = infoCollectionBean.result
        if (list.size>0){
            my_collection_rcycle.visibility = View.VISIBLE
            my_collection_rcycle.adapter = CollectionListAdapter(this,list)
        }else{
            no_sreach_img.visibility = View.VISIBLE
        }
    }

    override fun onFail() {

    }

    override fun onDestroy() {
        super.onDestroy()
        if (infoCollectionPre != null){
            infoCollectionPre.detachView()
        }
    }
}
