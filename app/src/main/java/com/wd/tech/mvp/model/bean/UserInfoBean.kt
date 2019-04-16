package com.wd.tech.mvp.model.bean

data class UserInfoBean(
    val message: String,
    val result: UserInfoBeanResult,
    val status: String
)

data class UserInfoBeanResult(
    val headPic: String,
    val integral: Int,
    val nickName: String,
    val phone: String,
    val sex: Int,
    val userId: Int,
    val userName: String,
    val whetherFaceId: Int,
    val whetherVip: Int
)