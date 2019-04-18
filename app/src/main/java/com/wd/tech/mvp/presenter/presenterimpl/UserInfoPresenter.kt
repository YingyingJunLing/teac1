package com.wd.tech.mvp.presenter.presenterimpl

import com.wd.tech.mvp.model.api.ApiServer
import com.wd.tech.mvp.model.bean.UserInfoBean
import com.wd.tech.mvp.model.bean.UserSignBean
import com.wd.tech.mvp.model.utils.RetrofitUtil
import com.wd.tech.mvp.presenter.base.BasePresenter
import com.wd.tech.mvp.view.activity.MainActivity
import com.wd.tech.mvp.view.contract.Contract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class UserInfoPresenter(var mainActivity: MainActivity) : BasePresenter<Contract.IUserInfoView>(),Contract.IUserInfoPre {

    var apiServer : ApiServer = RetrofitUtil.instant.SSLRetrofit()

    override fun onIUserSignPre(hashMap: HashMap<String, String>) {
        apiServer.getUserSign(hashMap)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<UserSignBean>(){
                override fun onComplete() {

                }

                override fun onNext(t: UserSignBean) {
                    mainActivity.onSignSuccess(t)
                }

                override fun onError(e: Throwable) {

                }
            })
    }

    override fun onIUserInfoPre(hashMap: HashMap<String, String>) {
        apiServer.getUserInfo(hashMap)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<UserInfoBean>(){
                override fun onComplete() {

                }

                override fun onNext(t: UserInfoBean) {
                    mainActivity.onSuccess(t)
                }

                override fun onError(e: Throwable) {

                }
            })
    }
}