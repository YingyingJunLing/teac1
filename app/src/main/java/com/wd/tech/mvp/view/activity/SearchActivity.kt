package com.wd.tech.mvp.view.activity


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.FindAllInfoPlate
import com.wd.tech.mvp.presenter.presenterimpl.InfoSelectPre
import com.wd.tech.mvp.view.adapter.SearchAllItemAdapters
import com.wd.tech.mvp.view.base.BaseActivity
import com.wd.tech.mvp.view.contract.Contract
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity<Contract.IInfoSelectView, InfoSelectPre>(),Contract.IInfoSelectView, View.OnClickListener {
    var infoSelectPre:InfoSelectPre?=null
    val hashMap = HashMap<String,String>()
    override fun createPresenter(): InfoSelectPre? {
        infoSelectPre=InfoSelectPre(this)
        return infoSelectPre
    }

    override fun initActivityView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_search)
        var sharedPreferences : SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)
        var userId = sharedPreferences.getString("userId", "0")
        var sessionId = sharedPreferences.getString("sessionId", "0")
        hashMap.put("userId",userId)
        hashMap.put("sessionId",sessionId)
    }

    override fun initData() {
        infoSelectPre!!.onfindAllInfoPlate(hashMap)
    }

    override fun initView() {
        text_finsh.setOnClickListener(this)
        search_edit.setOnClickListener(this)
        sou_recy.setLayoutManager(GridLayoutManager(this,3))
    }

    override fun onSuccess(any: Any) {
        if(any is FindAllInfoPlate)
        {
            var adapter= SearchAllItemAdapters(this,any)
            sou_recy.adapter = adapter
        }

    }

    override fun onFail() {

    }

    override fun onClick(v: View?) {
        when(v!!.id)
        {
          R.id.text_finsh->{
              finish()
          }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        if(infoSelectPre !=null)
        {
            infoSelectPre!!.detachView()
        }
    }
}
