package com.wd.tech.mvp.model.api

import com.wd.tech.mvp.model.api.Api.Companion.ADDGREATERECORD
import com.wd.tech.mvp.model.api.Api.Companion.BANNERSHOW
import com.wd.tech.mvp.model.api.Api.Companion.CANCELGREATE
import com.wd.tech.mvp.model.api.Api.Companion.COMMUNITYLIST
import com.wd.tech.mvp.model.api.Api.Companion.DETALICOMMENT
import com.wd.tech.mvp.model.api.Api.Companion.FINDINFOADVERTISING
import com.wd.tech.mvp.model.api.Api.Companion.INFODETAIL
import com.wd.tech.mvp.model.api.Api.Companion.INFORECOMMENEDLIST
import com.wd.tech.mvp.model.api.Api.Companion.LOGIN
import com.wd.tech.mvp.model.api.Api.Companion.MODIFYHEADPIC
import com.wd.tech.mvp.model.api.Api.Companion.REG
import com.wd.tech.mvp.model.api.Api.Companion.USERINFOBYUSERID
import com.wd.tech.mvp.model.api.Api.Companion.USERSIGN
import com.wd.tech.mvp.model.api.Api.Companion.WECHATLOGIN
import com.wd.tech.mvp.model.bean.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*


interface ApiServer {
    //轮播
    @GET(BANNERSHOW)
    fun getBannerShow(): Observable<BannerShowBean>

    //资讯推荐展示列表(包含单独板块列表展示)
    @GET(INFORECOMMENEDLIST)
    fun getInfoRecommendList(@Query("plateId") plateId: Int, @Query("page") page: Int, @Query("count") count: Int): Observable<InfoRecommendListBean>

    //咨询广告
    @GET(FINDINFOADVERTISING)
    fun getfindInfoAdvertising(): Observable<InfoAdvertisingBean>

    //咨询详情页面
    @GET(INFODETAIL)
    fun getInfoDetail(@Query("id") id: Int): Observable<InfoDetailBean>

    //咨询详情评论列表
    @GET(DETALICOMMENT)
    fun getInfoDetailComment(@Query("infoId") id: Int, @Query("page") page: Int, @Query("count") count: Int): Observable<DetailCommentBean>

    //咨询点赞
    @POST(ADDGREATERECORD)
    @FormUrlEncoded
    fun getAddGreatRecord(@HeaderMap hashMap: HashMap<String, String>, @Field("infoId")infoId:Int):Observable<AddGreatRecordBean>

    //咨询取消点赞
    @POST(CANCELGREATE)
    @FormUrlEncoded
    fun getCancelGreate(@HeaderMap hashMap: HashMap<String, String>, @Field("infoId")infoId:Int):Observable<CancelGreateBean>


    //微信登录
    @POST(WECHATLOGIN)
    @FormUrlEncoded
    fun getWeChatLogin(@Header("ak") ak:String ,@Field("code") code: String): Observable<LoginBean>

    //登录
    @POST(LOGIN)
    @FormUrlEncoded
    fun getLogin(@Field("phone") phone: String, @Field("pwd") pwd: String): Observable<LoginBean>

    //注册
    @POST(REG)
    @FormUrlEncoded
    fun getReg(@Field("phone") phone: String, @Field("nickName") nickName: String, @Field("pwd") pwd: String): Observable<RegBean>

    //社区列表
    @GET(COMMUNITYLIST)
    fun getCommunityList(@HeaderMap hashMap: HashMap<String, String>, @Query("page") page: Int, @Query("count") count: Int): Observable<CommunityListBean>

    //根据Id查询用户信息
    @GET(USERINFOBYUSERID)
    fun getUserInfo(@HeaderMap hashMap: HashMap<String, String>): Observable<UserInfoBean>

    //用户签到
    @POST(USERSIGN)
    fun getUserSign(@HeaderMap hashMap: HashMap<String, String>): Observable<UserSignBean>

    //关注列表
    @GET(INFORECOMMENEDLIST)
    fun getInfoCollection(@HeaderMap hashMap: HashMap<String, String>, @Query("page") page: Int, @Query("count") count: Int): Observable<InfoCollectionBean>

    //用户上传头像
    @POST(MODIFYHEADPIC)
    @Multipart
    fun getModifyHeadPic(@HeaderMap hashMap: HashMap<String, String>,@Part part : MultipartBody.Part): Observable<ModifyHeadPicBean>
}