package com.wd.tech.mvp.model.bean

data class FindFriendNoticePageListBean(
    val message: String,
    val result: List<FindFriendNoticePageListBeanResult>,
    val status: String
)

data class FindFriendNoticePageListBeanResult(
    val fromHeadPic: String,
    val fromNickName: String,
    val fromUid: Int,
    val noticeId: Int,
    val noticeTime: Long,
    val receiveUid: Int,
    val remark: String,
    val status: Int
)