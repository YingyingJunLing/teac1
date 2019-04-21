package com.wd.tech.mvp.presenter.presenterimpl

import com.wd.tech.mvp.model.bean.InfoRecommendListBean
import com.wd.tech.mvp.model.utils.RetrofitUtil
import com.wd.tech.mvp.presenter.base.BasePresenter
import com.wd.tech.mvp.view.activity.AllPlateItemActivity
import com.wd.tech.mvp.view.contract.Contract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class FindAllInfoPlateByIDPresenter(val allPlateItemActivity: AllPlateItemActivity ): BasePresenter<Contract.IfindAllInfoPlateByIDView>(),Contract.ofindAllInfoPlateByIDPre {
    override fun onfindAllInfoPlateByID( hashMap : HashMap<String,String>,plateId:Int,page:Int,count:Int) {
        val sslRetrofit = RetrofitUtil.instant.SSLRetrofit()
        sslRetrofit.getInfoById(hashMap,plateId,page,count)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<InfoRecommendListBean>() {
                override fun onComplete() {

                }

                override fun onNext(t: InfoRecommendListBean) {
                    allPlateItemActivity.onSuccess(t)
                }

                override fun onError(e: Throwable) {

                }
            })
    }
}