package com.wd.tech.mvp.presenter.presenterimpl

import com.wd.tech.mvp.model.api.ApiServer
import com.wd.tech.mvp.model.utils.RetrofitUtil
import com.wd.tech.mvp.presenter.base.BasePresenter
import com.wd.tech.mvp.view.activity.MainActivity
import com.wd.tech.mvp.view.contract.Contract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class UserInfoPresenter(var mainActivity: MainActivity) : BasePresenter<Contract.IUserInfoView>(),Contract.IUserInfoPre {

    override fun onIUserInfoPre(hashMap: HashMap<String, String>) {
        var apiServer : ApiServer = RetrofitUtil.instant.SSLRetrofit()
        apiServer.getUserInfo(hashMap)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer {
                mainActivity.onSuccess(it)
            })
    }
}