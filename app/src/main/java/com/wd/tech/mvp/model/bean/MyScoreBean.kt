package com.wd.tech.mvp.model.bean

data class MyScoreBean(
    var message: String,
    var result: Result,
    var status: String
)

data class MyScoreBeanResult(
    var amount: Int,
    var id: Int,
    var updateTime: Long,
    var userId: Int
)