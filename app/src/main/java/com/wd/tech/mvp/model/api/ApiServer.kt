package com.wd.tech.mvp.model.api

import com.wd.tech.mvp.model.api.Api.Companion.BANNERSHOW
import com.wd.tech.mvp.model.api.Api.Companion.COMMUNITYLIST
import com.wd.tech.mvp.model.api.Api.Companion.FINDINFOADVERTISING
import com.wd.tech.mvp.model.api.Api.Companion.INFODETAIL
import com.wd.tech.mvp.model.api.Api.Companion.INFORECOMMENEDLIST
import com.wd.tech.mvp.model.api.Api.Companion.LOGIN
import com.wd.tech.mvp.model.api.Api.Companion.REG
import com.wd.tech.mvp.model.bean.*
import io.reactivex.Observable
import retrofit2.http.*



interface ApiServer {
    //轮播
    @GET(BANNERSHOW)
    fun getBannerShow() : Observable<BannerShowBean>

    //资讯推荐展示列表(包含单独板块列表展示)
    @GET(INFORECOMMENEDLIST)
    fun getInfoRecommendList(@Query("plateId")plateId:Int,@Query("page")page:Int,@Query("count")count:Int):Observable<InfoRecommendListBean>

    //咨询广告
    @GET(FINDINFOADVERTISING)
    fun getfindInfoAdvertising():Observable<InfoAdvertisingBean>
    //咨询详情页面
    @GET(INFODETAIL)
    fun getInfoDetail(@Query("id")id:String):Observable<InfoDetailBean>
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