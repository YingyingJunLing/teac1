package com.wd.tech.mvp.presenter.presenterimpl

import com.wd.tech.mvp.model.bean.FindAllInfoPlate
import com.wd.tech.mvp.model.bean.InfoPayByIntegralBean
import com.wd.tech.mvp.model.utils.RetrofitUtil
import com.wd.tech.mvp.presenter.base.BasePresenter
import com.wd.tech.mvp.view.activity.ScoreDuiHuanActivity
import com.wd.tech.mvp.view.contract.Contract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class InfoPayByIntegralPresenter(val scoreDuiHuanActivity: ScoreDuiHuanActivity): BasePresenter<Contract.IInfoPayByIntegralView>(),Contract.IInfoPayByIntegralPre {
    override fun onIInfoPayByIntegralPre(hashMap: HashMap<String, String>, infoId: Int, integralCost: Int) {
            val sslRetrofit = RetrofitUtil.instant.SSLRetrofit()
            sslRetrofit.getinfoPayByIntegral(hashMap,infoId,integralCost)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DisposableObserver<InfoPayByIntegralBean>() {
                    override fun onComplete() {

                    }

                    override fun onNext(t: InfoPayByIntegralBean) {
                        scoreDuiHuanActivity.onSuccess(t)
                    }

                    override fun onError(e: Throwable) {

                    }
                })
    }
}