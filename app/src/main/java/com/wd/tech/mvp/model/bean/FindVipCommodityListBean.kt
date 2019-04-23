package com.wd.tech.mvp.model.bean

data class FindVipCommodityListBean(
    var message: String,
    var result: List<FindVipCommodityListBeanResult>,
    var status: String
)

data class FindVipCommodityListBeanResult(
    var commodityId: Int,
    var commodityName: String,
    var imageUrl: String,
    var price: Double
)