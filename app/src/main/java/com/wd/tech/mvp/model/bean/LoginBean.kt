package com.wd.tech.mvp.model.bean

data class LoginBean(
    val message: String,
    val result: LoginBeanResult,
    val status: String
)

data class LoginBeanResult(
    val nickName: String,
    val phone: String,
    val pwd: String,
    val sessionId: String,
    val userId: Int,
    val userName: String,
    val whetherFaceId: Int,
    val whetherVip: Int
)