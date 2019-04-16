package com.wd.tech.mvp.presenter.presenterimpl

import com.wd.tech.mvp.model.bean.LoginBean
import com.wd.tech.mvp.model.utils.RetrofitUtil
import com.wd.tech.mvp.presenter.base.BasePresenter
import com.wd.tech.mvp.view.activity.LoginActivity
import com.wd.tech.mvp.view.contract.Contract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers


class LoginPresenter(val loginActivity: LoginActivity) : BasePresenter<Contract.ILoginView>(),Contract.ILoginPre {

    override fun onILoinPre(phone: String, pwd: String) {
        val sslRetrofit = RetrofitUtil.instant.SSLRetrofit()
        sslRetrofit.getLogin(phone,pwd)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :DisposableObserver<LoginBean>(){
                override fun onComplete() {

                }

                override fun onNext(t: LoginBean) {
                    loginActivity.onSuccess(t)
                }

                override fun onError(e: Throwable) {

                }
            })
    }
}