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
        //微信登录
        const val WECHATLOGIN="techApi/user/v1/weChatLogin"
        //登录
        const val LOGIN:String="techApi/user/v1/login"
        //注册
        const val REG="techApi/user/v1/register"
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
        //用户发布帖子
        const val RELEASEPOST="techApi/community/verify/v1/releasePost"
        //查询用户所有分组
        const val FRIENDGROUPLIST="techApi/chat/verify/v1/findFriendGroupList"
    }
}