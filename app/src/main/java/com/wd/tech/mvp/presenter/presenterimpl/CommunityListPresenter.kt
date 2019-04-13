package com.wd.tech.mvp.presenter.presenterimpl

import com.wd.tech.mvp.model.api.ApiServer
import com.wd.tech.mvp.model.utils.RetrofitUtil
import com.wd.tech.mvp.presenter.base.BasePresenter
import com.wd.tech.mvp.view.contract.Contract
import com.wd.tech.mvp.view.frag.CommunityFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class CommunityListPresenter(val communityFragment: CommunityFragment) : BasePresenter<Contract.ICommunityListView>(),Contract.ICommunityListPre {

    override fun onICommunityListPre(hashMap: HashMap<String, Int>, page: Int, count: Int) {
        val apiServer : ApiServer = RetrofitUtil.instant.SSLRetrofit()
        apiServer.getCommunityList(hashMap,page,count)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(Consumer {
                communityFragment.onSuccess(it)
            });
    }
}