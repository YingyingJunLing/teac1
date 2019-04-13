package com.wd.tech.mvp.view.contract

import com.wd.tech.mvp.model.bean.CommunityListBean
import com.wd.tech.mvp.model.bean.LoginBean
import com.wd.tech.mvp.model.bean.RegBean

class Contract
{
    /**
     * 登录
     */
    interface ILoginView
    {
        fun onSuccess(loginBean: LoginBean)
        fun onFail()
    }
    interface ILoginPre
    {
      fun onILoinPre(phone:String,pwd:String)
    }
    /**
     * 注册
     */
    interface IRegView
    {
        fun onSuccess(regBean: RegBean)
        fun onFail()
    }
    interface IRegPre
    {
        fun onIRegPre(phone:String,nickName:String,pwd:String)
    }
    /**
     * 社区类表
     */
    interface ICommunityListView{
        fun onSuccess(communityListBean: CommunityListBean)
        fun onFail()
    }
    interface ICommunityListPre{
        fun onICommunityListPre(hashMap: HashMap<String,Int>,page:Int,count:Int)
    }
}