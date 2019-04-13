package com.wd.tech.mvp.model.bean

data class BannerShowBean(
    val message: String,
    val result: List<BannerShowBeanResult>,
    val status: String
)

data class BannerShowBeanResult(
    val imageUrl: String,
    val jumpUrl: String,
    val rank: Int,
    val title: String
)