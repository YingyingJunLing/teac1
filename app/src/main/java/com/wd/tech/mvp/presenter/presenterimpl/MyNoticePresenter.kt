package com.wd.tech.mvp.presenter.presenterimpl

import com.wd.tech.mvp.model.bean.MyNoticeBean
import com.wd.tech.mvp.model.utils.RetrofitUtil
import com.wd.tech.mvp.presenter.base.BasePresenter
import com.wd.tech.mvp.view.activity.MyNoticeActivity
import com.wd.tech.mvp.view.contract.Contract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MyNoticePresenter(val myNoticeActivity: MyNoticeActivity) : BasePresenter<Contract.IMyNoticeView>(),Contract.IMyNoticePre {


    override fun onIMyNoticePre(hashMap: HashMap<String, String>, page: Int, count: Int) {
        val sslRetrofit = RetrofitUtil.instant.SSLRetrofit()
        sslRetrofit.getMyNotice(hashMap,page,count)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<MyNoticeBean>(){
                override fun onNext(t: MyNoticeBean) {
                    myNoticeActivity.onSuccess(t)
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {

                }
            })
    }
}