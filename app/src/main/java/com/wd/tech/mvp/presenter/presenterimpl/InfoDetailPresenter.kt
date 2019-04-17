package com.wd.tech.mvp.presenter.presenterimpl

import android.util.Log
import com.wd.tech.mvp.model.bean.DetailCommentBean
import com.wd.tech.mvp.model.bean.InfoDetailBean
import com.wd.tech.mvp.model.utils.RetrofitUtil
import com.wd.tech.mvp.presenter.base.BasePresenter
import com.wd.tech.mvp.view.activity.DetailsActivity
import com.wd.tech.mvp.view.contract.Contract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class InfoDetailPresenter (val detailsActivity: DetailsActivity): BasePresenter<Contract.IInfoDetailView>(),Contract.IInfoDetailPre {
    override fun onDetailCommentPre(infoId: Int, page: Int, count: Int) {
        val sslRetrofit = RetrofitUtil.instant.SSLRetrofit()
        sslRetrofit.getInfoDetailComment(infoId,page,count)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<DetailCommentBean>() {
                override fun onComplete() {

                }
                override fun onNext(t: DetailCommentBean) {
                    detailsActivity.onDetailCommentSuccess(t)
                }

                override fun onError(e: Throwable) {

                }
            })

    }

    override fun onInfoDetailPre(id: Int) {
        val sslRetrofit = RetrofitUtil.instant.SSLRetrofit()
        sslRetrofit.getInfoDetail(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<InfoDetailBean>() {
                override fun onComplete() {

                }

                override fun onNext(t: InfoDetailBean) {

                    detailsActivity.onSuccess(t)
                }

                override fun onError(e: Throwable) {

                }
            })

    }

}