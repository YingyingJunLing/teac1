package com.wd.tech.mvp.presenter.presenterimpl

import com.wd.tech.mvp.model.api.ApiServer
import com.wd.tech.mvp.model.bean.CommunityListBean
import com.wd.tech.mvp.model.utils.RetrofitUtil
import com.wd.tech.mvp.presenter.base.BasePresenter
import com.wd.tech.mvp.view.contract.Contract
import com.wd.tech.mvp.view.frag.CommunityFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class CommunityListPresenter(val communityFragment: CommunityFragment) : BasePresenter<Contract.ICommunityListView>(),Contract.ICommunityListPre {

    val apiServer : ApiServer = RetrofitUtil.instant.SSLRetrofit()

    override fun onICommunityListPre(hashMap: HashMap<String, String>, page: Int, count: Int) {
        apiServer.getCommunityList(hashMap,page,count)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableObserver<CommunityListBean>(){
                override fun onComplete() {

                }

                override fun onNext(t: CommunityListBean) {
                    communityFragment.onSuccess(t)
                }

                override fun onError(e: Throwable) {

                }
            })
    }

    override fun onIPushCommunityListPre(hashMap: HashMap<String, String>, page: Int, count: Int) {
        apiServer.getCommunityList(hashMap,page,count)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableObserver<CommunityListBean>(){
                override fun onComplete() {

                }

                override fun onNext(t: CommunityListBean) {
                    communityFragment.onIPushSuccess(t)
                }

                override fun onError(e: Throwable) {

                }
            })
    }
}