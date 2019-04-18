package com.wd.tech.mvp.presenter.presenterimpl

import com.wd.tech.mvp.model.api.ApiServer
import com.wd.tech.mvp.model.bean.ReleasePostBean
import com.wd.tech.mvp.model.utils.RetrofitUtil
import com.wd.tech.mvp.presenter.base.BasePresenter
import com.wd.tech.mvp.view.activity.UserPushCommentActivity
import com.wd.tech.mvp.view.contract.Contract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class UserPushCommentPresenter(var userPushCommentActivity: UserPushCommentActivity) : BasePresenter<Contract.IUserPushCommentView>(),Contract.IUserPushCommentPre {

    var apiServer : ApiServer = RetrofitUtil.instant.SSLRetrofit()

    override fun onIUserPushCommentPre(hashMap: HashMap<String, String>, content: String, file: File) {
        val body = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val createFormData = MultipartBody.Part.createFormData("image", file.name, body)
        apiServer.getReleasePost(hashMap,content,createFormData)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<ReleasePostBean>(){
                override fun onComplete() {

                }

                override fun onNext(t: ReleasePostBean) {
                    userPushCommentActivity.onSuccess(t)
                }

                override fun onError(e: Throwable) {

                }
            })
    }
}