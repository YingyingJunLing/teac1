package com.wd.tech.mvp.view.activity

import android.os.Bundle
import android.widget.Toast
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.InfoDetailBean
import com.wd.tech.mvp.presenter.presenterimpl.InfoDetailPresenter
import com.wd.tech.mvp.view.base.BaseActivity
import com.wd.tech.mvp.view.contract.Contract
import kotlinx.android.synthetic.main.activity_details.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

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

    }

    override fun initView() {

    }

    override fun onSuccess(any: Any) {
        if(any is InfoDetailBean)
        {
            if(any !=null)
            {
                val result = any.getResult()
                detail_title.text= result?.title
                detail_summary.text=result?.summary
                detail_source.text=result?.source
            }
        }

    }

    override fun onFail() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        EventBus.getDefault().register(this)
    }
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    fun getEventBus(id:String)
    {
        Toast.makeText(this,id,Toast.LENGTH_LONG).show()
    }

}
