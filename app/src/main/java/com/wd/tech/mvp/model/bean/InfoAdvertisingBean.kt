package com.wd.tech.mvp.model.bean

data class InfoAdvertisingBean(
    val message: String,
    val result: InfoAdvertisingBeanResult,
    val status: String
)

data class InfoAdvertisingBeanResult(
    val content: String,
    val id: Int,
    val pic: String,
    val url: String
)