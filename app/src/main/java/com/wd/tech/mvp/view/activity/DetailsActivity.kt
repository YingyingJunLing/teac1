package com.wd.tech.mvp.view.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.InfoDetailBean
import com.wd.tech.mvp.presenter.presenterimpl.InfoDetailPresenter
import com.wd.tech.mvp.view.base.BaseActivity
import com.wd.tech.mvp.view.contract.Contract
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : BaseActivity<Contract.IInfoDetailView,InfoDetailPresenter>(),Contract.IInfoDetailView {
    var infoDetailPresenter:InfoDetailPresenter?=null
    override fun createPresenter(): InfoDetailPresenter? {
        infoDetailPresenter = InfoDetailPresenter(this)
        return infoDetailPresenter
    }

    override fun initActivityView(savedInstanceState: Bundle?)
    {
        setContentView(R.layout.activity_details)
    }

    override fun initData() {
        val intent = intent
        var sid =  intent.getIntExtra("id",0)
        Toast.makeText(this,sid.toString(),Toast.LENGTH_LONG).show()
        infoDetailPresenter?.onInfoDetailPre(sid)
    }

    override fun initView() {

    }

    override fun onSuccess(any: Any) {
        if(any is InfoDetailBean)
        {
            if(any !=null)
            {
                val result = any.getResult()
                Log.e("onSuccess",any.toString())
                detail_title.text= result?.title
                detail_summary.text=result?.summary
                detail_source.text=result?.source
            }
        }
    }

    override fun onFail() {

    }

    override fun onDestroy() {
        super.onDestroy()
        if(infoDetailPresenter!=null)
        {
            infoDetailPresenter!!.detachView()
        }
    }

}


