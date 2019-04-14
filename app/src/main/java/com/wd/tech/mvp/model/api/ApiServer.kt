package com.wd.tech.mvp.model.api

import com.wd.tech.mvp.model.api.Api.Companion.BANNERSHOW
import com.wd.tech.mvp.model.api.Api.Companion.COMMUNITYLIST
import com.wd.tech.mvp.model.api.Api.Companion.LOGIN
import com.wd.tech.mvp.model.api.Api.Companion.REG
import com.wd.tech.mvp.model.bean.BannerShowBean
import com.wd.tech.mvp.model.bean.CommunityListBean
import com.wd.tech.mvp.model.bean.LoginBean
import com.wd.tech.mvp.model.bean.RegBean
import io.reactivex.Observable
import retrofit2.http.*

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
    fun getReg(@Field("phone")phone:String,@Field("nickName")nickName:String,@Field("pwd")pwd:String): Observable<RegBean>

    //社区列表
    @GET(COMMUNITYLIST)
    fun getCommunityList(@HeaderMap hashMap: HashMap<String,Int>,@Query("page")page:Int,@Query("count")count:Int): Observable<CommunityListBean>

}