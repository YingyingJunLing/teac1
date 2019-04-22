package com.wd.tech.mvp.model.bean

data class InitFriendListBean(
    val message: String,
    val result: List<InitFriendListBeanResult>,
    val status: String
)

data class InitFriendListBeanResult(
    val black: Int,
    val currentNumber: Int,
    val customize: Int,
    val friendInfoList: List<FriendListGroupByIdBeanResult>,
    val groupId: Int,
    val groupName: String
)