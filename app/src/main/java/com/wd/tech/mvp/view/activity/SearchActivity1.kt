package com.wd.tech.mvp.view.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.FindAllInfoPlate
import com.wd.tech.mvp.presenter.presenterimpl.FindAllInfoPlatePresenter
import com.wd.tech.mvp.view.adapter.AllPlateAdapter
import com.wd.tech.mvp.view.base.BaseActivity
import com.wd.tech.mvp.view.contract.Contract
import kotlinx.android.synthetic.main.activity_search1.*

class SearchActivity1 : BaseActivity<Contract.IfindAllInfoPlateView,FindAllInfoPlatePresenter>(),Contract.IfindAllInfoPlateView {
    var findAllInfoPlatePresenter:FindAllInfoPlatePresenter?=null
    val hashMap = HashMap<String,String>()
    override fun createPresenter(): FindAllInfoPlatePresenter? {
        findAllInfoPlatePresenter = FindAllInfoPlatePresenter(this)
        return findAllInfoPlatePresenter
    }

    override fun initActivityView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_search1)

    }

    override fun initData() {
        findAllInfoPlatePresenter!!.onfindAllInfoPlate(hashMap)
    }

    override fun initView() {
        allPlate_recy.layoutManager =GridLayoutManager(this,2)
        var sharedPreferences : SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)
        var userId = sharedPreferences.getString("userId", "0")
        var sessionId = sharedPreferences.getString("sessionId", "0")
        hashMap.put("userId",userId)
        hashMap.put("sessionId",sessionId)
    }

    override fun onSuccess(any: Any) {
        if(any is FindAllInfoPlate)
        {
            allPlate_recy?.visibility = View.VISIBLE
            loading_linear?.visibility = View.GONE
            var adapter = AllPlateAdapter(this,any)
            allPlate_recy.adapter = adapter
        }
    }

    override fun onFail() {

    }

    override fun onDestroy() {
        super.onDestroy()
        if(findAllInfoPlatePresenter !=null)
        {
            findAllInfoPlatePresenter!!.detachView()
        }
    }
}
