package com.wd.tech.mvp.presenter.presenterimpl

import com.wd.tech.mvp.model.bean.BindFaceBean
import com.wd.tech.mvp.model.utils.RetrofitUtil
import com.wd.tech.mvp.presenter.base.BasePresenter
import com.wd.tech.mvp.view.activity.PeopleActivity
import com.wd.tech.mvp.view.contract.Contract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class FaceRegPresenter(val peo:PeopleActivity) : BasePresenter<Contract.IMyFaceRegView>(),Contract.IMyFaceRegPre
{
    override fun onIMyFacePre(hashMap: HashMap<String, String>, featureInfo: String) {
        val sslRetrofit = RetrofitUtil.instant.SSLRetrofit()
        sslRetrofit.getBindMyFace(hashMap,featureInfo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<BindFaceBean>(){
                override fun onComplete() {

                }

                override fun onNext(t: BindFaceBean) {
                    peo.onSuccess(t)
                }

                override fun onError(e: Throwable) {

                }
            })
    }
}