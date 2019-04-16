package com.wd.tech.mvp.model.bean

data class InfoCollectionBean(
    val message: String,
    val result: List<InfoCollectionBeanResult>,
    val status: String
)

data class InfoCollectionBeanResult(
    val createTime: Long,
    val id: Int,
    val infoId: Int,
    val thumbnail: String,
    val title: String
)