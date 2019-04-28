package com.wd.tech.mvp.view.frag

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.util.Log
import android.view.View
import android.widget.Toast
import com.jcodecraeer.xrecyclerview.ProgressStyle
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.wd.tech.R
import com.wd.tech.mvp.presenter.presenterimpl.InformationPresenter
import com.wd.tech.mvp.view.activity.SearchActivity
import com.wd.tech.mvp.view.activity.SearchActivity1
import com.wd.tech.mvp.view.adapter.BannerAdapter
import com.wd.tech.mvp.view.base.BaseFragment
import com.wd.tech.mvp.view.contract.Contract
import kotlinx.android.synthetic.main.informationfragment.*
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import android.widget.PopupWindow
import com.wd.tech.mvp.model.bean.*

class InformationFragment : BaseFragment<Contract.IInformationView, InformationPresenter>(), Contract.IInformationView,
    View.OnClickListener {

    var informationPresenter: InformationPresenter? = null
    lateinit var bannerAdapter: BannerAdapter
    var infoRecommendListBean:InfoRecommendListBean?=null
    var page: Int = 1
    var count: Int = 10
   var bannerShowBean:BannerShowBean?=null
    var hashMap : HashMap<String,String> = HashMap()


    override fun setView(): Int {
        return R.layout.informationfragment
    }

    override fun initFragmentData(savedInstanceState: Bundle?) {
        informationPresenter!!.onIBannerPre()
        informationPresenter!!.onInfoRecommendList(hashMap,page, count)
        //收藏的点击事件
        onClickCollection()
        //分享的点击事件
        onShareClick()
    }



    override fun initFragmentChildView(view: View) {

        //搜索按钮点击
        inform_fdj.setOnClickListener(this)
        //所有模块查询点击事件
        inform_sdg.setOnClickListener(this)
        var sharedPreferences : SharedPreferences = context!!. getSharedPreferences("User", Context.MODE_PRIVATE)
        var userId = sharedPreferences.getString("userId", "0")
        var sessionId = sharedPreferences.getString("sessionId", "0")
        hashMap.put("userId",userId)
        hashMap.put("sessionId",sessionId)
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
                        informationPresenter?.onInfoRecommendList(hashMap,page, count)
                    }
                }, 1500)
                infomation_recy.refreshComplete()
            }
            override fun onLoadMore() {
                Handler().postDelayed(object : Runnable {
                    override fun run() {
                        count++
                        informationPresenter?.onInfoRecommendList( hashMap,page, count)
                        infomation_recy.loadMoreComplete()
                        bannerAdapter.notifyDataSetChanged()
                    }
                }, 1500)
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

    /**
     * 轮播图
     */
    override fun onSuccessBanner(any: Any) {
        if (any is BannerShowBean) {
            if (any != null) {
                bannerShowBean = any
            }
        }
    }

    /**
     * 咨询展示
     */
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
    /**
     * 咨询收藏
     */
    override fun onIAddGreatRecordSucccess(any: Any) {
        if(any is InformationCollcetionBean)
        {
            Toast.makeText(activity,any.getMessage(),Toast.LENGTH_LONG).show()
        }

    }
    /**
     * 咨询取消收藏
     */
    override fun onICancelGreaSucccess(any: Any) {
       if(any is InformationCollcetionBeanNo)
        {
            Toast.makeText(activity,any.getMessage(),Toast.LENGTH_LONG).show()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        if (informationPresenter != null) {
            informationPresenter!!.detachView()
        }
    }

    /**
     * 所有按钮点击事件
     */
    override fun onClick(v: View?)
    {
        when(v!!.id)
        {
            //搜索
            R.id.inform_fdj->{
                startActivity(Intent(activity,SearchActivity::class.java))
            }
            //兴趣分类
            R.id.inform_sdg->
            {
                startActivity(Intent(activity, SearchActivity1::class.java))
            }

        }

    }
    /**
     * 收藏的事件
     */
    private fun onClickCollection() {
        bannerAdapter.setOnClick(object :BannerAdapter.OnClick{
            override fun getdata(id: Int, great: Int, position: Int) {
                Log.e("lallalalla", "收到了吗")
                var sharedPreferences : SharedPreferences = context!!. getSharedPreferences("User", Context.MODE_PRIVATE)
                var userId = sharedPreferences.getString("userId", "0")
                var sessionId = sharedPreferences.getString("sessionId", "0")
                hashMap.put("userId",userId)
                hashMap.put("sessionId",sessionId)
                Toast.makeText(activity, great, Toast.LENGTH_LONG).show()
                if (userId == null || sessionId == null) {
                    Toast.makeText(activity, "您还没有登录，请先登录哟！", Toast.LENGTH_LONG).show()
                    return
                } else {
                    if (great == 1) {
                        //已点赞，需取消
                        informationPresenter!!.onICancelCollectionPre(hashMap, id)
                        bannerAdapter.getcancel(position)
                    } else {
                        informationPresenter!!.onIAddCollectionPre(hashMap, id)
                        bannerAdapter.getlike(position)
                    }
                }
            }
        })

    }

    //分享的点击事件
    fun onShareClick() {
        val view = View.inflate(context, R.layout.pop_share, null)

        val popupWindow = PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true)
        val cdw = ColorDrawable(resources.getColor(R.color.time))
        //设置颜色
        popupWindow.setBackgroundDrawable(cdw)
        popupWindow.setFocusable(true)
        bannerAdapter.setOnShareClickLisenter(object : BannerAdapter.onShareClick {
            override fun onShareClickLisenter(id: Int) {
                popupWindow.showAsDropDown(getView(), 1, -1)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        informationPresenter!!.onInfoRecommendList(hashMap,page, count)
    }
}

