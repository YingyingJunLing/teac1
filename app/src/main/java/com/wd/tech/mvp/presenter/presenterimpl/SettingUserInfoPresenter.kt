package com.wd.tech.mvp.presenter.presenterimpl

import com.wd.tech.mvp.model.api.ApiServer
import com.wd.tech.mvp.model.bean.ModifyHeadPicBean
import com.wd.tech.mvp.model.bean.UserInfoBean
import com.wd.tech.mvp.model.utils.RetrofitUtil
import com.wd.tech.mvp.presenter.base.BasePresenter
import com.wd.tech.mvp.view.activity.MySettingActivity
import com.wd.tech.mvp.view.contract.Contract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class SettingUserInfoPresenter(var mySettingActivity: MySettingActivity) : BasePresenter<Contract.ISettingUserInfoView>(),Contract.ISettingUserInfoPre {

    var apiServer : ApiServer = RetrofitUtil.instant.SSLRetrofit()

    override fun onSettingIUserInfoPre(hashMap: HashMap<String, String>) {
        apiServer.getUserInfo(hashMap)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<UserInfoBean>(){
                override fun onComplete() {

                }

                override fun onNext(t: UserInfoBean) {
                    mySettingActivity.onSuccess(t)
                }

                override fun onError(e: Throwable) {

                }
            })
    }

    override fun onModifyHeadPicPre(hashMap: HashMap<String, String>, file: File) {
        var requestFile : RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file)
        var filePart : MultipartBody.Part = MultipartBody.Part.createFormData("image",file.name,requestFile)
        apiServer.getModifyHeadPic(hashMap,filePart)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<ModifyHeadPicBean>(){
                override fun onComplete() {

                }

                override fun onNext(t: ModifyHeadPicBean) {
                    mySettingActivity.onModifyHeadPicSuccess(t)
                }

                override fun onError(e: Throwable) {

                }
            })
    }
}