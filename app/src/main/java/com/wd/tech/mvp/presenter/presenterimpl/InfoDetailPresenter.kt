package com.wd.tech.mvp.presenter.presenterimpl

import com.wd.tech.mvp.model.bean.*
import com.wd.tech.mvp.model.utils.RetrofitUtil
import com.wd.tech.mvp.presenter.base.BasePresenter
import com.wd.tech.mvp.view.activity.DetailsActivity
import com.wd.tech.mvp.view.contract.Contract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.http.HeaderMap
import retrofit2.http.QueryMap

class InfoDetailPresenter (val detailsActivity: DetailsActivity): BasePresenter<Contract.IInfoDetailView>(),Contract.IInfoDetailPre {
    override fun onIAddInfoCommentPre(hashMap: HashMap<String, String>, infoId: Int, content: String) {
        val sslRetrofit = RetrofitUtil.instant.SSLRetrofit()
        sslRetrofit.getAddInfoComment(hashMap,infoId,content)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<AddInfoCommentBean>() {
                override fun onComplete() {

                }

                override fun onNext(t: AddInfoCommentBean) {

                    detailsActivity.onSuccess(t)
                }

                override fun onError(e: Throwable) {


                }
            })
    }

    override fun onIAddCollectionPre(hashMap: HashMap<String, String>, infoId: Int) {
        val sslRetrofit = RetrofitUtil.instant.SSLRetrofit()
        sslRetrofit.getAddCollection(hashMap,infoId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<InformationCollcetionBean>() {
                override fun onComplete() {

                }

                override fun onNext(t: InformationCollcetionBean) {

                    detailsActivity.onSuccess(t)
                }

                override fun onError(e: Throwable) {


                }
            })
    }

    override fun onICancelCollectionPre(hashMap: HashMap<String, String>, infoId: Int) {
        val sslRetrofit = RetrofitUtil.instant.SSLRetrofit()
        sslRetrofit.getCancelcollection(hashMap,infoId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<InformationCollcetionBeanNo>() {
                override fun onComplete() {

                }
                override fun onNext(t: InformationCollcetionBeanNo) {
                    detailsActivity.onSuccess(t)

                }

                override fun onError(e: Throwable) {


                }
            })
    }

    override fun onIAddGreatRecordPre(hashMap: HashMap<String, String>, map: HashMap<String, String>) {
        val sslRetrofit = RetrofitUtil.instant.SSLRetrofit()
        sslRetrofit.getAddGreatRecord(hashMap,map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<AddGreatRecordBean>() {
                override fun onComplete() {

                }

                override fun onNext(t: AddGreatRecordBean) {
                    detailsActivity.onSuccess(t)
                }

                override fun onError(e: Throwable) {


                }
            })
    }

    override fun onICancelGreaPre(hashMap: HashMap<String, String> ,map: HashMap<String, String>) {
        val sslRetrofit = RetrofitUtil.instant.SSLRetrofit()
        sslRetrofit.getCancelGreate(hashMap,map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<CancelGreateBean>() {
                override fun onComplete() {

                }
                override fun onNext(t: CancelGreateBean) {
                    detailsActivity.onSuccess(t)

                }

                override fun onError(e: Throwable) {


                }
            })

    }

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

    override fun onInfoDetailPre(hashMap: HashMap<String, String>,id: Int) {
        val sslRetrofit = RetrofitUtil.instant.SSLRetrofit()
        sslRetrofit.getInfoDetail(hashMap,id)
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