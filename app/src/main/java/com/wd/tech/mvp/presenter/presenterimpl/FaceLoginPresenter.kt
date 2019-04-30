package com.wd.tech.mvp.presenter.presenterimpl

import android.util.Log
import com.wd.tech.mvp.model.bean.LoginBean
import com.wd.tech.mvp.model.utils.RetrofitUtil
import com.wd.tech.mvp.presenter.base.BasePresenter
import com.wd.tech.mvp.view.activity.DetecterActivity
import com.wd.tech.mvp.view.contract.Contract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class FaceLoginPresenter(val det:DetecterActivity) : BasePresenter<Contract.IMyFaceLoginView>() ,Contract.IMyFaceLoginPre{
    override fun onIMyFaceLoginPre( faceId: String) {
        val sslRetrofit = RetrofitUtil.instant.SSLRetrofit()
        sslRetrofit.getFaceLogin(faceId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<LoginBean>(){
                override fun onComplete() {

                }

                override fun onNext(t: LoginBean) {
                    Log.e("失败了","成功了")
                    det.onSuccess(t)
                }

                override fun onError(e: Throwable) {
                    Log.e("失败了","失败了")

                }
            })
    }
}