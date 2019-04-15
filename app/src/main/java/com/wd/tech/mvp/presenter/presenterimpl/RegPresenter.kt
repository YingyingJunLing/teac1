package com.wd.tech.mvp.presenter.presenterimpl

import android.util.Log
import com.wd.tech.mvp.model.bean.RegBean
import com.wd.tech.mvp.model.utils.RetrofitUtil
import com.wd.tech.mvp.presenter.base.BasePresenter
import com.wd.tech.mvp.view.activity.RegisterActivity
import com.wd.tech.mvp.view.contract.Contract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class RegPresenter(val registerActivity: RegisterActivity) : BasePresenter<Contract.IRegView>(),Contract.IRegPre {

    override fun onIRegPre(phone: String, nickName: String, pwd: String)
    {
        val apiServer = RetrofitUtil.instant.SSLRetrofit()
        apiServer.getReg(phone,nickName,pwd)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :DisposableObserver<RegBean>(){
                override fun onNext(t: RegBean) {
                    registerActivity.onSuccess(t)
                }

                override fun onComplete() {

                }



                override fun onError(e: Throwable) {
                           Log.e("onError","失败了")
                }
            })

    }
}


