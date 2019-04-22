package com.wd.tech.mvp.model.bean

data class FriendListGroupByIdBean(
    val message: String,
    val result: List<FriendListGroupByIdBeanResult>,
    val status: String
)

data class FriendListGroupByIdBeanResult(
    val friendUid: Int,
    val headPic: String,
    val nickName: String,
    val remarkName: String,
    val vipFlag: Int
)