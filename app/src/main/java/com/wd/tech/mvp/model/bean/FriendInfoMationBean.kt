package com.wd.tech.mvp.model.bean

data class FriendInfoMationBean(
    val message: String,
    val result: FriendInfoMationBeanResult,
    val status: String
)

data class FriendInfoMationBeanResult(
    val headPic: String,
    val integral: Int,
    val myGroupList: List<Any>,
    val nickName: String,
    val phone: String,
    val sex: Int,
    val userId: Int,
    val userName: String,
    val whetherFaceId: Int,
    val whetherVip: Int
)