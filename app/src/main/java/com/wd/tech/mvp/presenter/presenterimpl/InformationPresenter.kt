package com.wd.tech.mvp.presenter.presenterimpl

import android.util.Log
import com.wd.tech.mvp.model.bean.*
import com.wd.tech.mvp.model.utils.RetrofitUtil
import com.wd.tech.mvp.presenter.base.BasePresenter
import com.wd.tech.mvp.view.contract.Contract
import com.wd.tech.mvp.view.frag.InformationFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class InformationPresenter(val infoFragment: InformationFragment) : BasePresenter<Contract.IInformationView>(),Contract.IInformationPre {
    override fun onIAddCollectionPre(hashMap: HashMap<String, String>, infoId: Int) {
        val sslRetrofit = RetrofitUtil.instant.SSLRetrofit()
        sslRetrofit.getAddCollection(hashMap,infoId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<InformationCollcetionBean>() {
                override fun onComplete() {

                }

                override fun onNext(t: InformationCollcetionBean) {

                    infoFragment.onIAddGreatRecordSucccess(t)
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
                    infoFragment.onICancelGreaSucccess(t)

                }

                override fun onError(e: Throwable) {


                }
            })

    }

    val TAG="InformationPresenter"
    /**
     * 轮播图
     */
    override fun onIBannerPre() {
        val sslRetrofit = RetrofitUtil.instant.SSLRetrofit()
        sslRetrofit.getBannerShow()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<BannerShowBean>() {
                override fun onComplete() {

                }

                override fun onNext(t: BannerShowBean) {
                    infoFragment.onSuccessBanner(t)

                }

                override fun onError(e: Throwable) {


                }
            })

    }

    /**
     * 咨询展示页面
     */
    override fun onInfoRecommendList(hashMap: HashMap<String, String>,page:Int,count:Int) {
        val sslRetrofit = RetrofitUtil.instant.SSLRetrofit()
        sslRetrofit.getInfoRecommendList(hashMap,page,count)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<InfoRecommendListBean>() {
                override fun onComplete() {

                }

                override fun onNext(t: InfoRecommendListBean) {
                    infoFragment.onSuccessInfoRecommendList(t)
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG,"失败了")
                }
            })

    }

    /**
     * 广告展示
     */
    override fun onInfoAdvertising() {
        val sslRetrofit = RetrofitUtil.instant.SSLRetrofit()
        sslRetrofit.getfindInfoAdvertising()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<InfoAdvertisingBean>() {
                override fun onComplete() {

                }
                override fun onNext(t: InfoAdvertisingBean) {

                }

                override fun onError(e: Throwable) {
                    Log.e(TAG,"失败了")
                }
            })
    }
}