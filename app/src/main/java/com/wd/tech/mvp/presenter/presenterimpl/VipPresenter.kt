package com.wd.tech.mvp.presenter.presenterimpl

import android.util.Log
import com.wd.tech.mvp.model.bean.ByVipBean
import com.wd.tech.mvp.model.bean.FindVipCommodityListBean
import com.wd.tech.mvp.model.bean.InfoRecommendListBean
import com.wd.tech.mvp.model.utils.RetrofitUtil
import com.wd.tech.mvp.presenter.base.BasePresenter
import com.wd.tech.mvp.view.activity.PayVipActivity
import com.wd.tech.mvp.view.contract.Contract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class VipPresenter(val payVipActivity: PayVipActivity) : BasePresenter<Contract.IVipView>(),Contract.IVipPre {
    override fun onIByVip(hashMap: HashMap<String, String>, commodityId: Int, sign: String) {
        val sslRetrofit = RetrofitUtil.instant.SSLRetrofit()
        sslRetrofit.getByVip(hashMap,commodityId,sign)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<ByVipBean>() {
                override fun onComplete() {

                }

                override fun onNext(t: ByVipBean) {
                    payVipActivity.onSuccess(t)
                }

                override fun onError(e: Throwable) {
                    Log.e("失败了","akakkalallall")
                }
            })
    }

    override fun onIFindVipCommodityListPre() {
        val sslRetrofit = RetrofitUtil.instant.SSLRetrofit()
        sslRetrofit.getFindVipCommodityList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<FindVipCommodityListBean>() {
                override fun onComplete() {

                }

                override fun onNext(t: FindVipCommodityListBean) {
                    payVipActivity.onSuccess(t)
                }

                override fun onError(e: Throwable) {
                  Log.e("失败了","akakkalallall")
                }
            })
    }
}