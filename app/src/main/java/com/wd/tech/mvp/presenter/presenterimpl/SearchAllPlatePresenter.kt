package com.wd.tech.mvp.presenter.presenterimpl

import com.wd.tech.mvp.model.bean.BySourceBean
import com.wd.tech.mvp.model.bean.ByTitelBean
import com.wd.tech.mvp.model.bean.InfoRecommendListBean
import com.wd.tech.mvp.model.utils.RetrofitUtil
import com.wd.tech.mvp.presenter.base.BasePresenter
import com.wd.tech.mvp.view.activity.SearchAllPlateActivity
import com.wd.tech.mvp.view.contract.Contract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class SearchAllPlatePresenter (val searchAllPlateActivity: SearchAllPlateActivity) :
    Contract.oInfoSelectPlatePre, BasePresenter<Contract.IInfoSelectPlateView>()  {
    override fun onfindAllInfoPlateByID(hashMap: HashMap<String, String>,plateId: Int, page: Int, count: Int) {
        val sslRetrofit = RetrofitUtil.instant.SSLRetrofit()
        sslRetrofit.getInfoById(hashMap,plateId,page,count)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<InfoRecommendListBean>() {
                override fun onComplete() {

                }

                override fun onNext(t: InfoRecommendListBean) {
                    searchAllPlateActivity.onSuccess(t)
                }

                override fun onError(e: Throwable) {

                }
            })
    }

    override fun onByTitle(title: String, page: Int, count: Int) {
        val sslRetrofit = RetrofitUtil.instant.SSLRetrofit()
        sslRetrofit.getByTitle(title,page,count)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<ByTitelBean>() {
                override fun onComplete() {

                }

                override fun onNext(t: ByTitelBean) {
                    searchAllPlateActivity.onSuccess(t)

                }

                override fun onError(e: Throwable) {

                }
            })
    }

    override fun onBySource(source: String, page: Int, count: Int) {
        val sslRetrofit = RetrofitUtil.instant.SSLRetrofit()
        sslRetrofit.getBySource(source,page,count)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<BySourceBean>() {
                override fun onComplete() {

                }

                override fun onNext(t: BySourceBean) {
                    searchAllPlateActivity.onSuccess(t)

                }

                override fun onError(e: Throwable) {

                }
            })
    }


}