package com.wd.tech.mvp.model.bean

data class FriendGroupListBean(
    val message: String,
    val result: List<FriendGroupListBeanResult>,
    val status: String
)

data class FriendGroupListBeanResult(
    val currentNumber: Int,
    val customize: Int,
    val groupId: Int,
    val groupName: String
)