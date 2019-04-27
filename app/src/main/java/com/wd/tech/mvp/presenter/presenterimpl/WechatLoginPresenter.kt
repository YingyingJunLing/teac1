package com.wd.tech.mvp.presenter.presenterimpl

import android.util.Log
import com.wd.tech.mvp.model.bean.LoginBean
import com.wd.tech.mvp.model.utils.RetrofitUtil
import com.wd.tech.mvp.presenter.base.BasePresenter
import com.wd.tech.mvp.view.contract.Contract
import com.wd.tech.wxapi.WXEntryActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class WechatLoginPresenter( val wxEntryActivity:WXEntryActivity) : BasePresenter<Contract.IWechatLoginView>(),Contract.IWechatLoginPre {

    override fun onIWechatLoginPre(ak:String,code: String) {
        val sslRetrofit = RetrofitUtil.instant.SSLRetrofit()
        sslRetrofit.getWeChatLogin(ak,code)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<LoginBean>() {
                override fun onComplete() {

                }

                override fun onNext(t: LoginBean) {
                    Log.e("成功","成功")
                    wxEntryActivity?.onIWechatLoginSuccess(t)
                }

                override fun onError(e: Throwable) {
                    Log.e("失败了","失败了")

                }
            })

    }
}