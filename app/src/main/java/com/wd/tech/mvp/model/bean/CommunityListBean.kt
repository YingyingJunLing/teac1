package com.wd.tech.mvp.model.bean

data class CommunityListBean(
    val message: String,
    val result: List<CommunityListBeanResult>,
    val status: String
)

data class CommunityListBeanResult(
    val comment: Int,
    val communityCommentVoList: List<CommunityCommentVo>,
    val content: String,
    val `file`: String,
    val headPic: String,
    val id: Int,
    val nickName: String,
    val praise: Int,
    val publishTime: Long,
    val signature: String,
    val userId: Int,
    val whetherFollow: Int,
    val whetherGreat: Int,
    val whetherVip: Int
)

data class CommunityCommentVo(
    val content: String,
    val nickName: String,
    val userId: Int
)