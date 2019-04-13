package com.wd.tech.mvp.model.api

class Api {
    companion object {
        val BASE_URl : String ="https://mobile.bwstudent.com/"
        //轮播
        const val BANNERSHOW : String ="techApi/information/v1/bannerShow"
        //登录
        const val LOGIN:String="techApi/user/v1/login"
        //注册
        const val REG="techApi/user/v1/register"
        //社区列表
        const val COMMUNITYLIST="techApi/community/v1/findCommunityList"
    }
}