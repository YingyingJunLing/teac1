package com.wd.tech.mvp.model.api

import com.wd.tech.mvp.model.api.Api.Companion.BANNERSHOW
import com.wd.tech.mvp.model.api.Api.Companion.LOGIN
import com.wd.tech.mvp.model.api.Api.Companion.REG
import com.wd.tech.mvp.model.bean.BannerShowBean
import com.wd.tech.mvp.model.bean.LoginBean
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiServer {
    //轮播
    @GET(BANNERSHOW)
    fun getBannerShow() : Observable<BannerShowBean>

    //登录
    @POST(LOGIN)
    @FormUrlEncoded
    fun getLogin(@Field("phone")phone:String,@Field("pwd")pwd:String): Observable<LoginBean>

    //注册
    @POST(REG)
    @FormUrlEncoded
    fun getReg(@Field("phone")phone:String,@Field("nickName")nickName:String,@Field("pwd")pwd:String): Observable<LoginBean>
}