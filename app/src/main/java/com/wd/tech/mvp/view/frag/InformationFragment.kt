package com.wd.tech.mvp.view.frag

import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.util.Log
import android.view.View
import com.jcodecraeer.xrecyclerview.ProgressStyle
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.BannerShowBean
import com.wd.tech.mvp.model.bean.InfoRecommendListBean
import com.wd.tech.mvp.presenter.presenterimpl.InformationPresenter
import com.wd.tech.mvp.view.adapter.BannerAdapter
import com.wd.tech.mvp.view.base.BaseFragment
import com.wd.tech.mvp.view.contract.Contract
import kotlinx.android.synthetic.main.informationfragment.*

class InformationFragment : BaseFragment<Contract.IInformationView, InformationPresenter>(), Contract.IInformationView {
    var informationPresenter: InformationPresenter? = null
    lateinit var bannerAdapter: BannerAdapter
    var infoRecommendListBean:InfoRecommendListBean?=null
    var plateId: Int = 1
    var page: Int = 1
    var count: Int = 10
   var bannerShowBean:BannerShowBean?=null
    override fun setView(): Int {
        return R.layout.informationfragment
    }

    override fun initFragmentData(savedInstanceState: Bundle?) {
        informationPresenter!!.onIBannerPre()
        informationPresenter!!.onInfoRecommendList(plateId, page, count)
    }

    override fun initFragmentChildView(view: View) {
        //设置布局样式
        var linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = OrientationHelper.VERTICAL
        infomation_recy.setLayoutManager(linearLayoutManager)
        infomation_recy.setPullRefreshEnabled(true)
        infomation_recy.setLoadingMoreEnabled(true)
        infomation_recy.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader)
        infomation_recy.setLoadingMoreProgressStyle(ProgressStyle.Pacman)
        infomation_recy.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onRefresh() {
                Handler().postDelayed(object : Runnable {
                    override fun run() {
                        page = 1
                        count = 10
                        plateId = 1
                        informationPresenter?.onInfoRecommendList(plateId, page, count)
                    }
                }, 1500)
                infomation_recy.refreshComplete()
            }
            override fun onLoadMore() {

            }

        })
    }

    override fun createPresenter(): InformationPresenter? {
        bannerAdapter = BannerAdapter(context, bannerShowBean, infoRecommendListBean)
        informationPresenter = InformationPresenter(this)
        return informationPresenter
    }
    override fun onFail() {

    }

    override fun onSuccessBanner(any: Any) {
        if (any is BannerShowBean) {
            if (any != null) {
                bannerShowBean = any
            }
        }
    }

    override fun onSuccessInfoRecommendList(any: Any) {
        infomation_recy?.visibility = View.VISIBLE
        loading_linear_info?.visibility = View.GONE
        if (any is InfoRecommendListBean) {
            if(any !=null)
            {
                infoRecommendListBean = any
                infomation_recy.adapter = BannerAdapter(context,bannerShowBean, infoRecommendListBean!!)
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        if (informationPresenter != null) {
            informationPresenter!!.detachView()
        }
    }
}

