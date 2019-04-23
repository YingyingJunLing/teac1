package com.wd.tech.mvp.view.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.view.View
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.InfoRecommendListBean
import com.wd.tech.mvp.presenter.presenterimpl.FindAllInfoPlateByIDPresenter
import com.wd.tech.mvp.view.adapter.AllPlateItemAdapter
import com.wd.tech.mvp.view.base.BaseActivity
import com.wd.tech.mvp.view.contract.Contract
import kotlinx.android.synthetic.main.activity_all_plate_item.*


class AllPlateItemActivity : BaseActivity<Contract.IfindAllInfoPlateByIDView,FindAllInfoPlateByIDPresenter>() ,Contract.IfindAllInfoPlateByIDView,
    View.OnClickListener {
    val hashMap = HashMap<String,String>()
    override fun onClick(v: View?) {
        when(v!!.id)
        {
            R.id.inform_sdg->
            {
                finish()
            }
            R.id.inform_fdj->
            {
                startActivity(Intent(this@AllPlateItemActivity,SearchActivity::class.java))
            }
        }
    }

    var findAllInfoPlateByIDPresenter:FindAllInfoPlateByIDPresenter?=null
    var sid :Int  =0

    override fun createPresenter(): FindAllInfoPlateByIDPresenter? {
        findAllInfoPlateByIDPresenter = FindAllInfoPlateByIDPresenter(this)
       return findAllInfoPlateByIDPresenter
    }

    override fun initActivityView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_all_plate_item)
        var sharedPreferences : SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)
        var userId = sharedPreferences.getString("userId", "0")
        var sessionId = sharedPreferences.getString("sessionId", "0")
        hashMap.put("userId",userId)
        hashMap.put("sessionId",sessionId)

    }


    override fun initData() {
        findAllInfoPlateByIDPresenter!!.onfindAllInfoPlateByID(hashMap,sid,page = 1,count = 10)
    }

    override fun initView() {
        inform_sdg.setOnClickListener(this)
        inform_fdj.setOnClickListener(this)
        val intent = intent
        var id =  intent.getIntExtra("id",0)
        sid = id
        plate_item_recy.layoutManager = LinearLayoutManager(this,OrientationHelper.VERTICAL,false)
    }

    override fun onSuccess(any: Any) {
        if(any is InfoRecommendListBean)
        {
            loading_linear_info.visibility =View.GONE
            plate_item_recy.visibility =View.VISIBLE
            var adapter = AllPlateItemAdapter(this,any)
            plate_item_recy.adapter = adapter
        }

    }

    override fun onFail() {

    }

    override fun onDestroy() {
        super.onDestroy()
        if(findAllInfoPlateByIDPresenter !=null)
        {
            findAllInfoPlateByIDPresenter!!.detachView()
        }
    }
}
