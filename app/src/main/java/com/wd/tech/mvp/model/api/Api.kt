package com.wd.tech.mvp.model.api

class Api {
    companion object {
        val BASE_URl : String ="https://mobile.bwstudent.com/"
        //轮播
        const val BANNERSHOW : String ="techApi/information/v1/bannerShow"
        //咨询展示页面
        const val INFORECOMMENEDLIST="techApi/information/v1/infoRecommendList"
        //咨询广告
        const val FINDINFOADVERTISING="techApi/information/v1/findInfoAdvertising"
        //咨询详情页面
        const val INFODETAIL="techApi/information/v1/findInformationDetails"
        //咨询详情评论列表
        const val DETALICOMMENT="techApi/information/v1/findAllInfoCommentList"
        //咨询点赞
        const val ADDGREATERECORD="techApi/information/verify/v1/addGreatRecord"
        //咨询取消点赞
        const val CANCELGREATE="techApi/information/verify/v1/cancelGreat"
        //根据咨询标题查询
        const val BYTITLE="techApi/information/v1/findInformationByTitle"
        //根据咨询作者名查询
        const val BYSOURCE="techApi/information/v1/findInformationBySource"
        //所有版块的查询
        const val FINDAllINFOPLATE="techApi/information/v1/findAllInfoPlate"
        //根据版块的id进行查询
        const val INFORECOMMENEDLISTBYID="techApi/information/v1/infoRecommendList"
        //微信登录
        const val WECHATLOGIN="techApi/user/v1/weChatLogin"
        //登录
        const val LOGIN:String="techApi/user/v1/login"
        //注册
        const val REG="techApi/user/v1/register"
        //添加用户对咨询详情的评论
        const val AddInfoComment="techApi/information/verify/v1/addInfoComment"
        //社区列表
        const val COMMUNITYLIST="techApi/community/v1/findCommunityList"
        //根据Id查询用户信息
        const val USERINFOBYUSERID="techApi/user/verify/v1/getUserInfoByUserId"
        //用户签到
        const val USERSIGN="techApi/user/verify/v1/userSign"
        //关注列表
        const val INFOCOLLECTIONLIST="techApi/user/verify/v1/findAllInfoCollection"
        //用户上传头像
        const val MODIFYHEADPIC="techApi/user/verify/v1/modifyHeadPic"
        //咨询收藏
        const val ADDCPPLECTION="techApi/user/verify/v1/addCollection"
        //咨询取消收藏
        const val CANCELCLOOECTION="techApi/user/verify/v1/cancelCollection"
        //用户发布帖子
        const val RELEASEPOST="techApi/community/verify/v1/releasePost"
        //查询用户所有分组
        const val FRIENDGROUPLIST="techApi/chat/verify/v1/findFriendGroupList"
        //积分兑换
        const val infoPayByIntegral="techApi/information/verify/v1/infoPayByIntegral"
        //查询会员所有商品
        const val FindVipCommodityList="techApi/tool/v1/findVipCommodityList"
        //购买生成订单
        const val BuyVip="techApi/tool/verify/v1/buyVip"
        //支付
        const val PAY="techApi/tool/verify/v1/pay"
        //查询分组下的好友列表信息
        const val FRIENDLISTBYGROUPID="techApi/chat/verify/v1/findFriendListByGroupId"
        //初始化好友列表
        const val INITFRIENDLIST="techApi/chat/verify/v1/initFriendList"
        //点赞
        const val ADDCOMMUNITYGREAT="techApi/community/verify/v1/addCommunityGreat"
        //点赞
        const val CANCELCOMMUNITYGREAT="techApi/community/verify/v1/cancelCommunityGreat"
        // 社区评论
        const val ADDCOMMUNITYCOMMENT="techApi/community/verify/v1/addCommunityComment"
        //查询我的帖子
        const val MyCard="techApi/community/verify/v1/findMyPostById"
        //查询我的通知
        const val MyNotice="techApi/tool/verify/v1/findSysNoticeList"
        //删除帖子
        const val DELETEPIST="techApi/community/verify/v1/deletePost"
        //根据用户ID查询用户信息
        const val FRIENDINFOMATION="techApi/user/verify/v1/queryFriendInformation"
        //绑定脸部
        const val BindingFaceId="techApi/user/verify/v1/bindingFaceId"
        //刷脸登录
        const val FaceLogin="techApi/user/v1/faceLogin"

    }

}