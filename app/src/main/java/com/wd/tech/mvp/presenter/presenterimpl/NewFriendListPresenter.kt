package com.wd.tech.mvp.presenter.presenterimpl

import com.wd.tech.mvp.model.api.ApiServer
import com.wd.tech.mvp.model.bean.FindFriendNoticePageListBean
import com.wd.tech.mvp.model.bean.ReviewFriendApplyBean
import com.wd.tech.mvp.model.utils.RetrofitUtil
import com.wd.tech.mvp.presenter.base.BasePresenter
import com.wd.tech.mvp.view.activity.NewFriendListActivity
import com.wd.tech.mvp.view.contract.Contract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class NewFriendListPresenter(var newFriendListActivity: NewFriendListActivity) : BasePresenter<Contract.INewFriendListView>(),Contract.INewFriendListPre {

    var apiServer : ApiServer = RetrofitUtil.instant.SSLRetrofit()

    override fun onINewFriendListPre(hashMap: HashMap<String, String>, page: Int, count: Int) {
        apiServer.getFindFriendNoticePageList(hashMap,page,count)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<FindFriendNoticePageListBean>(){
                override fun onComplete() {

                }

                override fun onNext(t: FindFriendNoticePageListBean) {
                    newFriendListActivity.onINewFriendListSuccess(t)
                }

                override fun onError(e: Throwable) {

                }
            })
    }

    override fun onIReviewFriend(hashMap: HashMap<String, String>, noticeId: Int, flag: Int) {
        apiServer.getReviewFriendApply(hashMap,noticeId,flag)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<ReviewFriendApplyBean>(){
                override fun onComplete() {

                }

                override fun onNext(t: ReviewFriendApplyBean) {
                    newFriendListActivity.onIReviewFriend(t)
                }

                override fun onError(e: Throwable) {

                }
            })
    }
}