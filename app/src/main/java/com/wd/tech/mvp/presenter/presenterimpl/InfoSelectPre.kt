package com.wd.tech.mvp.presenter.presenterimpl

import com.wd.tech.mvp.model.bean.BySourceBean
import com.wd.tech.mvp.model.bean.ByTitelBean
import com.wd.tech.mvp.model.bean.FindAllInfoPlate
import com.wd.tech.mvp.model.utils.RetrofitUtil
import com.wd.tech.mvp.presenter.base.BasePresenter
import com.wd.tech.mvp.view.activity.SearchActivity
import com.wd.tech.mvp.view.contract.Contract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class InfoSelectPre(val searchActivity: SearchActivity) :Contract.oInfoSelectPre, BasePresenter<Contract.IInfoSelectView>() {
    override fun onfindAllInfoPlate(hashMap: HashMap<String, String>) {
        val sslRetrofit = RetrofitUtil.instant.SSLRetrofit()
        sslRetrofit.getfindAllInfoPlate(hashMap)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<FindAllInfoPlate>() {
                override fun onComplete() {

                }

                override fun onNext(t: FindAllInfoPlate) {
                    searchActivity.onSuccess(t)

                }

                override fun onError(e: Throwable) {

                }
            })
    }

}