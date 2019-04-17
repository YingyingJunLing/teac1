package com.wd.tech.mvp.model.bean

data class UserInfoBean(
    val message: String,
    val result: Result,
    val status: String
)

data class Result(
    val email: String,
    val headPic: String,
    val integral: Int,
    val nickName: String,
    val phone: String,
    val sex: Int,
    val signature: String,
    val userId: Int,
    val whetherFaceId: Int,
    val whetherVip: Int
)