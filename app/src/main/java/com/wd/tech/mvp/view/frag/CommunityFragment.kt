package com.wd.tech.mvp.view.frag

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.wd.tech.R
import com.wd.tech.mvp.model.api.ApiServer
import com.wd.tech.mvp.model.bean.CommunityGreatBean
import com.wd.tech.mvp.model.bean.CommunityListBean
import com.wd.tech.mvp.model.bean.CommunityListBeanResult
import com.wd.tech.mvp.model.utils.RetrofitUtil
import com.wd.tech.mvp.presenter.presenterimpl.CommunityListPresenter
import com.wd.tech.mvp.view.activity.UserPushCommentActivity
import com.wd.tech.mvp.view.adapter.CommunityListAdapter
import com.wd.tech.mvp.view.base.BaseFragment
import com.wd.tech.mvp.view.contract.Contract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers.*
import kotlinx.android.synthetic.main.dialog_comment_layout.view.*
import kotlinx.android.synthetic.main.frag_community.view.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class CommunityFragment : BaseFragment<Contract.ICommunityListView,CommunityListPresenter>(),Contract.ICommunityListView {

    lateinit var communityListPresenter : CommunityListPresenter
    lateinit var adapter : CommunityListAdapter
    var hashMap : HashMap<String,String> = HashMap()
    var xRecycle_community : XRecyclerView ?= null
    var loading_linear : LinearLayout ?= null
    var page : Int = Random().nextInt(5)+1
    var list = ArrayList<CommunityListBeanResult>()
    var cid : Int = 0
    var cIndex : Int = 0

    override fun setView(): Int {
        return R.layout.frag_community
    }

    override fun initFragmentData(savedInstanceState: Bundle?) {
        var sharedPreferences : SharedPreferences = activity!!.getSharedPreferences("User", Context.MODE_PRIVATE)
        var userId = sharedPreferences.getString("userId", "0")
        var sessionId = sharedPreferences.getString("sessionId", "0")
        hashMap.put("userId",userId)
        hashMap.put("sessionId",sessionId)
        communityListPresenter.onICommunityListPre(hashMap,page,10)
    }

    override fun initFragmentChildView(view: View) {
        view.xRecycle_community.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        xRecycle_community = view.xRecycle_community
        loading_linear = view.loading_linear
        //点击编辑的按钮 也就是发表评论的按钮
        view.user_push_comment_image.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var intent : Intent = Intent(activity,UserPushCommentActivity::class.java)
                startActivity(intent)
            }
        })
    }

    override fun createPresenter(): CommunityListPresenter? {
        communityListPresenter = CommunityListPresenter(this)
        return communityListPresenter
    }

    override fun onSuccess(communityListBean: CommunityListBean) {
        xRecycle_community?.refreshComplete()
        xRecycle_community?.loadMoreComplete()
        xRecycle_community?.visibility = View.VISIBLE
        loading_linear?.visibility = View.GONE
        val result = communityListBean.result
        for(i in result){
            list.add(i)
        }
        if (list.size==result.size){
            adapter = CommunityListAdapter(activity!!,list)
            xRecycle_community?.adapter = adapter
        }
        xRecycle_community?.setLoadingListener(object : XRecyclerView.LoadingListener{
            override fun onLoadMore() {
                page++
                communityListPresenter.onICommunityListPre(hashMap,page,10)
            }

            override fun onRefresh() {
                list.clear()
                page = 1
                communityListPresenter.onICommunityListPre(hashMap,page,10)
            }
        })
        adapter.setIPushCommentListener(object : CommunityListAdapter.IPushComment{
            override fun onIPushCount(id : Int,pageIndex : Int,index : Int) {
                cid = id
                cIndex = index
                var view : View = LayoutInflater.from(context).inflate(R.layout.dialog_comment_layout,null)
                var builder : AlertDialog.Builder = AlertDialog.Builder(context)
                val dialog = builder.create()
                dialog.setView(view)
                builder.setCancelable(true)
                val window = dialog.getWindow()
                window.setGravity(Gravity.BOTTOM)
                dialog.show()
                view.push_text.setOnClickListener(object : View.OnClickListener{
                    override fun onClick(v: View?) {
                        var apiServer : ApiServer = RetrofitUtil.instant.SSLRetrofit()
                        apiServer.getAddCommunityComment(hashMap,id,view.push_edit.text.toString())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(io())
                            .subscribe(object : DisposableObserver<CommunityGreatBean>(){
                                override fun onComplete() {

                                }

                                override fun onNext(t: CommunityGreatBean) {
                                    Toast.makeText(context,t.message,Toast.LENGTH_LONG).show()
                                    dialog.dismiss()
                                    communityListPresenter.onIPushCommunityListPre(hashMap,pageIndex,10)
                                }

                                override fun onError(e: Throwable) {

                                }
                            })
                    }
                })
            }
        })
    }

    override fun onIPushSuccess(communityListBean: CommunityListBean) {
        if (list.get(cIndex).id == cid){
            list.removeAt(cIndex)
        }
        for (i in communityListBean.result){
            list.remove(i)
        }
        for (i in communityListBean.result){
            list.add(i)
        }
        adapter.notifyDataSetChanged()

    }

    override fun onFail() {

    }

    override fun onDestroy() {
        super.onDestroy()
        if (communityListPresenter != null){
            communityListPresenter.detachView()
        }
    }
}