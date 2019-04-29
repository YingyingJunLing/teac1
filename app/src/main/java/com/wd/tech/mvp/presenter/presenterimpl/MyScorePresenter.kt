package com.wd.tech.mvp.presenter.presenterimpl

import com.wd.tech.mvp.model.bean.MyScoreBean
import com.wd.tech.mvp.model.utils.RetrofitUtil
import com.wd.tech.mvp.presenter.base.BasePresenter
import com.wd.tech.mvp.view.activity.MyScoreActivity
import com.wd.tech.mvp.view.contract.Contract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MyScorePresenter(val myScoreActivity: MyScoreActivity) : BasePresenter<Contract.IMyScoreView>(),Contract.IMyScorePre {
    override fun onIMyScorePre(hashMap: HashMap<String, String>) {
        val sslRetrofit = RetrofitUtil.instant.SSLRetrofit()
        sslRetrofit.getMyScore(hashMap)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<MyScoreBean>(){
                override fun onComplete() {

                }

                override fun onNext(t: MyScoreBean) {
                    myScoreActivity.onSuccess(t)

                }

                override fun onError(e: Throwable) {

                }
            })
    }
}