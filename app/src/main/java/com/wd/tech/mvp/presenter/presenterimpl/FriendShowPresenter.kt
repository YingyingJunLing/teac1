package com.wd.tech.mvp.presenter.presenterimpl

import com.wd.tech.mvp.model.api.ApiServer
import com.wd.tech.mvp.model.bean.AddFriendBean
import com.wd.tech.mvp.model.bean.FindUserByPhoneBean
import com.wd.tech.mvp.model.bean.FriendInfoMationBean
import com.wd.tech.mvp.model.utils.RetrofitUtil
import com.wd.tech.mvp.presenter.base.BasePresenter
import com.wd.tech.mvp.view.activity.FriendShowActivity
import com.wd.tech.mvp.view.contract.Contract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class FriendShowPresenter(var friendShowActivity: FriendShowActivity) : BasePresenter<Contract.IFriendShowView>(),Contract.IFriendShowPre {

    val apiServer : ApiServer = RetrofitUtil.instant.SSLRetrofit()

    override fun onIFriendPhoneShowPre(hashMap: HashMap<String, String>, phone: String) {
        apiServer.getFindUserByPhone(hashMap,phone)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableObserver<FindUserByPhoneBean>(){
                override fun onComplete() {

                }

                override fun onNext(t: FindUserByPhoneBean) {
                    friendShowActivity.onPhoneSuccess(t)
                }

                override fun onError(e: Throwable) {

                }
            })
    }

    override fun onIFriendIdShowPre(hashMap: HashMap<String, String>, id: String) {
        apiServer.getFriendInfoMation(hashMap,id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableObserver<FriendInfoMationBean>(){
                override fun onComplete() {

                }

                override fun onNext(t: FriendInfoMationBean) {
                    friendShowActivity.onIdSuccess(t)
                }

                override fun onError(e: Throwable) {

                }
            })
    }

    override fun onIFriendAddShowPre(hashMap: HashMap<String, String>, friendUid: String, remark: String) {
        apiServer.getAddFriend(hashMap,friendUid,remark)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableObserver<AddFriendBean>(){
                override fun onComplete() {

                }

                override fun onNext(t: AddFriendBean) {
                    friendShowActivity.onAddSuccess(t)
                }

                override fun onError(e: Throwable) {

                }
            })
    }
}