package com.wd.tech.mvp.model.bean

data class DetailCommentBean(
    var message: String,
    var result: List<DetailCommentBeanResult>,
    var status: String
)

data class DetailCommentBeanResult(
    var commentTime: Long,
    var content: String,
    var headPic: String,
    var id: Int,
    var infoId: Int,
    var nickName: String,
    var userId: Int
)