package com.wd.tech.mvp.presenter.presenterimpl

import com.wd.tech.mvp.model.api.ApiServer
import com.wd.tech.mvp.model.bean.FriendGroupListBean
import com.wd.tech.mvp.model.bean.FriendListGroupByIdBean
import com.wd.tech.mvp.model.bean.InitFriendListBean
import com.wd.tech.mvp.model.utils.RetrofitUtil
import com.wd.tech.mvp.presenter.base.BasePresenter
import com.wd.tech.mvp.view.contract.Contract
import com.wd.tech.mvp.view.frag.FragMessagePeopleList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MessagePresenter(var fragMessagePeopleList: FragMessagePeopleList) : BasePresenter<Contract.IMessageView>(),Contract.IMessagePre {

    val apiServer : ApiServer = RetrofitUtil.instant.SSLRetrofit()

    override fun onIMessagePre(hashMap: HashMap<String, String>) {
        apiServer.getInitFriendList(hashMap)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableObserver<InitFriendListBean>(){
                override fun onComplete() {

                }

                override fun onNext(t: InitFriendListBean) {
                    fragMessagePeopleList.onSuccess(t)
                }

                override fun onError(e: Throwable) {

                }
            })
    }
}