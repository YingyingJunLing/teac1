package com.wd.tech.mvp.presenter.presenterimpl

import com.wd.tech.mvp.model.bean.LoginBean
import com.wd.tech.mvp.model.utils.RetrofitUtil
import com.wd.tech.mvp.presenter.base.BasePresenter
import com.wd.tech.mvp.view.contract.Contract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class WechatLoginPresenter : BasePresenter<Contract.IWechatLoginView>(),Contract.IWechatLoginPre {
    override fun onIWechatLoginPre(code: String) {
        val sslRetrofit = RetrofitUtil.instant.SSLRetrofit()
        sslRetrofit.getWeChatLogin(code)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<LoginBean>() {
                override fun onComplete() {

                }

                override fun onNext(t: LoginBean) {

                }

                override fun onError(e: Throwable) {

                }
            })

    }
}