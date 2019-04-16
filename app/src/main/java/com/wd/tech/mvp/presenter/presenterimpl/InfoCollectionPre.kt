package com.wd.tech.mvp.presenter.presenterimpl

import com.wd.tech.mvp.model.api.ApiServer
import com.wd.tech.mvp.model.utils.RetrofitUtil
import com.wd.tech.mvp.presenter.base.BasePresenter
import com.wd.tech.mvp.view.activity.MyCollectionActivity
import com.wd.tech.mvp.view.contract.Contract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class InfoCollectionPre(var myCollectionActivity: MyCollectionActivity) : BasePresenter<Contract.IInfoCollectionView>(),Contract.IInfoCollectionPre {

    var apiServer : ApiServer = RetrofitUtil.instant.SSLRetrofit()

    override fun onIInfoCollectionPre(hashMap: HashMap<String, String>, page: Int, count: Int) {
        apiServer.getInfoCollection(hashMap,page,count)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer {
                myCollectionActivity.onInfoCollectionSuccess(it)
            })
    }
}