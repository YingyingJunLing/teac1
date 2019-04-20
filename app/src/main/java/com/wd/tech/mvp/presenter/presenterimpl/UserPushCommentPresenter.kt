package com.wd.tech.mvp.presenter.presenterimpl

import android.util.Log
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
import retrofit2.http.PartMap
import java.io.File

class UserPushCommentPresenter(var userPushCommentActivity: UserPushCommentActivity) : BasePresenter<Contract.IUserPushCommentView>(),Contract.IUserPushCommentPre {

    var apiServer : ApiServer = RetrofitUtil.instant.SSLRetrofit()
    var partMap = HashMap<String,List<MultipartBody.Part>>()
    var partList = ArrayList<MultipartBody.Part>()

    override fun onIUserPushCommentPre(hashMap: HashMap<String, String>, content: String, list : ArrayList<File>) {
        for (i in list){
            var file : File = i
            val body = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val filePart = MultipartBody.Part.createFormData("file", file.name, body)
            partList.add(filePart)
            partMap.put("file",partList)
        }
        apiServer.getReleasePost(hashMap,content,partList)
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