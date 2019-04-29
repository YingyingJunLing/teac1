package com.wd.tech.mvp.model.api

import com.wd.tech.mvp.model.api.Api.Companion.ADDCOMMUNITYCOMMENT
import com.wd.tech.mvp.model.api.Api.Companion.ADDCOMMUNITYGREAT
import com.wd.tech.mvp.model.api.Api.Companion.ADDCPPLECTION
import com.wd.tech.mvp.model.api.Api.Companion.ADDGREATERECORD
import com.wd.tech.mvp.model.api.Api.Companion.AddInfoComment
import com.wd.tech.mvp.model.api.Api.Companion.BANNERSHOW
import com.wd.tech.mvp.model.api.Api.Companion.BYSOURCE
import com.wd.tech.mvp.model.api.Api.Companion.BYTITLE
import com.wd.tech.mvp.model.api.Api.Companion.BuyVip
import com.wd.tech.mvp.model.api.Api.Companion.CANCELCLOOECTION
import com.wd.tech.mvp.model.api.Api.Companion.CANCELCOMMUNITYGREAT
import com.wd.tech.mvp.model.api.Api.Companion.CANCELGREATE
import com.wd.tech.mvp.model.api.Api.Companion.COMMUNITYLIST
import com.wd.tech.mvp.model.api.Api.Companion.DELETEPIST
import com.wd.tech.mvp.model.api.Api.Companion.DETALICOMMENT
import com.wd.tech.mvp.model.api.Api.Companion.FINDAllINFOPLATE
import com.wd.tech.mvp.model.api.Api.Companion.FINDINFOADVERTISING
import com.wd.tech.mvp.model.api.Api.Companion.FRIENDGROUPLIST
import com.wd.tech.mvp.model.api.Api.Companion.FRIENDINFOMATION
import com.wd.tech.mvp.model.api.Api.Companion.FindVipCommodityList
import com.wd.tech.mvp.model.api.Api.Companion.FRIENDLISTBYGROUPID
import com.wd.tech.mvp.model.api.Api.Companion.FaceLogin
import com.wd.tech.mvp.model.api.Api.Companion.INFOCOLLECTIONLIST
import com.wd.tech.mvp.model.api.Api.Companion.INFODETAIL
import com.wd.tech.mvp.model.api.Api.Companion.INFORECOMMENEDLIST
import com.wd.tech.mvp.model.api.Api.Companion.INFORECOMMENEDLISTBYID
import com.wd.tech.mvp.model.api.Api.Companion.INITFRIENDLIST
import com.wd.tech.mvp.model.api.Api.Companion.LOGIN
import com.wd.tech.mvp.model.api.Api.Companion.MODIFYHEADPIC
import com.wd.tech.mvp.model.api.Api.Companion.MYSCORE
import com.wd.tech.mvp.model.api.Api.Companion.MyCard
import com.wd.tech.mvp.model.api.Api.Companion.MyNotice
import com.wd.tech.mvp.model.api.Api.Companion.PAY
import com.wd.tech.mvp.model.api.Api.Companion.REG
import com.wd.tech.mvp.model.api.Api.Companion.RELEASEPOST
import com.wd.tech.mvp.model.api.Api.Companion.USERINFOBYUSERID
import com.wd.tech.mvp.model.api.Api.Companion.USERSIGN
import com.wd.tech.mvp.model.api.Api.Companion.WECHATLOGIN
import com.wd.tech.mvp.model.api.Api.Companion.WXSHARE
import com.wd.tech.mvp.model.api.Api.Companion.infoPayByIntegral
import com.wd.tech.mvp.model.bean.*
import com.wd.tech.mvp.model.bean.MyCardActivityBean
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*


interface ApiServer {
    //轮播
    @GET(BANNERSHOW)
    fun getBannerShow(): Observable<BannerShowBean>

    //资讯推荐展示列表(包含单独板块列表展示)
    @GET(INFORECOMMENEDLIST)
    fun getInfoRecommendList(@HeaderMap hashMap: HashMap<String, String>,@Query("page") page: Int, @Query("count") count: Int): Observable<InfoRecommendListBean>

    //咨询广告
    @GET(FINDINFOADVERTISING)
    fun getfindInfoAdvertising(): Observable<InfoAdvertisingBean>

    //咨询详情页面
    @GET(INFODETAIL)
    fun getInfoDetail(@HeaderMap hashMap: HashMap<String, String>,@Query("id") id: Int): Observable<InfoDetailBean>

    //咨询详情评论列表
    @GET(DETALICOMMENT)
    fun getInfoDetailComment(@Query("infoId") id: Int, @Query("page") page: Int, @Query("count") count: Int): Observable<DetailCommentBean>

    //咨询点赞
    @POST(ADDGREATERECORD)
    fun getAddGreatRecord(@HeaderMap hashMap: HashMap<String, String>, @QueryMap map: HashMap<String, String>):Observable<AddGreatRecordBean>

    //咨询取消点赞
    @DELETE(CANCELGREATE)
    fun getCancelGreate(@HeaderMap hashMap: HashMap<String, String>, @QueryMap map: HashMap<String, String>):Observable<CancelGreateBean>
    //咨询收藏
    @POST(ADDCPPLECTION)
    @FormUrlEncoded
    fun getAddCollection(@HeaderMap hashMap: HashMap<String, String>, @Field("infoId")infoId:Int):Observable<InformationCollcetionBean>

    //咨询取消收藏
    @DELETE(CANCELCLOOECTION)
    fun getCancelcollection(@HeaderMap hashMap: HashMap<String, String>, @Query("infoId")infoId:Int):Observable<InformationCollcetionBeanNo>
    //用户评论
    @POST(AddInfoComment)
    @FormUrlEncoded
    fun getAddInfoComment(@HeaderMap hashMap: HashMap<String, String>, @Field("infoId")infoId:Int, @Field("content")content:String):Observable<AddInfoCommentBean>
    //所有的版块查询
    @GET(FINDAllINFOPLATE)
    fun getfindAllInfoPlate(@HeaderMap hashMap: HashMap<String, String>):Observable<FindAllInfoPlate>
    //资讯推荐展示列表(包含单独板块列表展示)
    @GET(INFORECOMMENEDLISTBYID)
    fun getInfoById(@HeaderMap hashMap: HashMap<String, String>,@Query("plateId") plateId: Int, @Query("page") page: Int, @Query("count") count: Int): Observable<InfoRecommendListBean>
    //根据作者名称进行查询
   @GET(BYSOURCE)
   fun getBySource(@Query("source")source:String,@Query("page")  page:Int,@Query("count")count:Int):Observable<BySourceBean>
    //根据标题名称进行查询
    @GET(BYTITLE)
    fun getByTitle(@Query("title")title:String,@Query("page")  page:Int,@Query("count")count:Int):Observable<ByTitelBean>
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
    @GET(INFOCOLLECTIONLIST)
    fun getInfoCollection(@HeaderMap hashMap: HashMap<String, String>, @Query("page") page: Int, @Query("count") count: Int): Observable<InfoCollectionBean>

    //用户上传头像
    @POST(MODIFYHEADPIC)
    @Multipart
    fun getModifyHeadPic(@HeaderMap hashMap: HashMap<String, String>,@Part part : MultipartBody.Part): Observable<ModifyHeadPicBean>
    //用户发布帖子
    @POST(RELEASEPOST)
    @Multipart
    fun getReleasePost(@HeaderMap hashMap: HashMap<String, String>, @Query("content")content: String, @Part list : List<MultipartBody.Part>): Observable<ReleasePostBean>
    //查询用户所有分组
    @GET(FRIENDGROUPLIST)
    fun getFriendGroupList(@HeaderMap hashMap: HashMap<String, String>): Observable<FriendGroupListBean>

    //积分兑换
    @POST(infoPayByIntegral)
    @FormUrlEncoded
    fun getinfoPayByIntegral(@HeaderMap hashMap: HashMap<String, String>,@Field("infoId")infoId: Int,@Field("integralCost")integralCost:Int):Observable<InfoPayByIntegralBean>
    //查询会员所有商品
    @GET(FindVipCommodityList)
    fun getFindVipCommodityList():Observable<FindVipCommodityListBean>
    //购买vip生成订单
    @POST(BuyVip)
    @FormUrlEncoded
    fun getByVip(@HeaderMap hashMap: HashMap<String, String>,@Field("commodityId")commodityId: Int,@Field("sign")sign:String):Observable<ByVipBean>
    //微信支付
    @POST(PAY)
    @FormUrlEncoded
    fun getWechatPay(@HeaderMap hashMap: HashMap<String, String>, @Field("orderId") orderId: String?, @Field("payType") payType:Int):Observable<WechatPayBean>
    //支付宝支付
    @POST(PAY)
    @FormUrlEncoded
    fun getZhiPay(@HeaderMap hashMap: HashMap<String, String>, @Field("orderId") orderId: String?, @Field("payType") payType:Int):Observable<ZhiFuBaoBean>

    //查询分组下的好友列表信息
    @GET(FRIENDLISTBYGROUPID)
    fun getFriendListGroupById(@HeaderMap hashMap: HashMap<String, String>,@Query("groupId")groupId: String): Observable<FriendListGroupByIdBean>

    //初始化好友类表
    @GET(INITFRIENDLIST)
    fun getInitFriendList(@HeaderMap hashMap: HashMap<String, String>): Observable<InitFriendListBean>

    //点赞
    @POST(ADDCOMMUNITYGREAT)
    fun getAddCommunityGreat(@HeaderMap hashMap: HashMap<String, String>,@Query("communityId")communityId : Int): Observable<CommunityGreatBean>

    //取消点赞
    @DELETE(CANCELCOMMUNITYGREAT)
    fun getCancelCommunityGreat(@HeaderMap hashMap: HashMap<String, String>,@Query("communityId")communityId : Int): Observable<CommunityGreatBean>

    //社区评论
    @POST(ADDCOMMUNITYCOMMENT)
    fun getAddCommunityComment(@HeaderMap hashMap: HashMap<String, String>,@Query("communityId")communityId: Int,@Query("content")content: String): Observable<CommunityGreatBean>
    //我的帖子
    @GET(MyCard)
    fun getMyCard(@HeaderMap hashMap: HashMap<String, String>,@Query("page") page: Int, @Query("count") count: Int):Observable<MyCardActivityBean>
    //我的通知
    @GET(MyNotice)
    fun getMyNotice(@HeaderMap hashMap: HashMap<String, String>,@Query("page") page: Int, @Query("count") count: Int):Observable<MyNoticeBean>
    //删除我的帖子
    @DELETE(DELETEPIST)
    fun getDeleltPost(@HeaderMap hashMap: HashMap<String, String>, @Query("communityId") id: String?):Observable<DeletePostBean>
    //根据用户ID查询用户信息
    @GET(FRIENDINFOMATION)
    fun getFriendInfoMation(@HeaderMap hashMap: HashMap<String, String>,@Query("friend") friend: String): Observable<FriendInfoMationBean>
    //绑定脸部
    @PUT(Api.BindingFaceId)
    fun getBindingFaceId(@HeaderMap hashMap: HashMap<String, String>,@Query("featureInfo") featureInfo: String): Observable<BindFaceBean>
    //刷脸登录
    @POST(FaceLogin)
    fun getFaceLogin(@Query("faceId")faceId:String):Observable<LoginBean>
    //根据手机号查询用户信息
    @GET(Api.FINDUSERBYPHONE)
    fun getFindUserByPhone(@HeaderMap hashMap: HashMap<String, String>,@Query("phone") phone: String): Observable<FindUserByPhoneBean>
    //添加好友
    @POST(Api.ADDFRIEND)
    fun getAddFriend(@HeaderMap hashMap: HashMap<String, String>,@Query("friendUid") friendUid: String,@Query("remark") remark: String): Observable<AddFriendBean>
    //微信分享
    @POST(WXSHARE)
    fun getWxShare(@FieldMap hashMap: java.util.HashMap<String, String>?):Observable<WxShareBean>
    //我的积分
    @GET(MYSCORE)
    fun getMyScore(@HeaderMap hashMap: HashMap<String, String>):Observable<MyScoreBean>

}