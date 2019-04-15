package com.wd.tech.mvp.model.bean

class InfoDetailBean
{
    private var result: ResultBean? = null
    private var message: String? = null
    private var status: String? = null

    fun getResult(): ResultBean? {
        return result
    }

    fun setResult(result: ResultBean) {
        this.result = result
    }

    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String) {
        this.message = message
    }

    fun getStatus(): String? {
        return status
    }

    fun setStatus(status: String) {
        this.status = status
    }

    class ResultBean {
        /**
         * comment : 0
         * id : 10
         * integralCost : 0
         * praise : 0
         * readPower : 2
         * releaseTime : 1539050842000
         * share : 0
         * source : 胡泳
         * summary : 不要着眼于互联网的下半场，而要放眼互联网的下一个大场。
         * thumbnail : https://img.huxiucdn.com/article/cover/201805/25/104319243667.jpg?imageView2/1/w/710/h/400/|imageMogr2/strip/interlace/1/quality/85/format/jpg
         * title : 腾讯须证明自己是一家科技公司
         * whetherCollection : 2
         * whetherGreat : 2
         * yuanCost : 0.12
         */

        var comment: Int = 0
        var id: Int = 0
        var integralCost: Int = 0
        var praise: Int = 0
        var readPower: Int = 0
        var releaseTime: Long = 0
        var share: Int = 0
        var source: String? = null
        var summary: String? = null
        var thumbnail: String? = null
        var title: String? = null
        var whetherCollection: Int = 0
        var whetherGreat: Int = 0
        var yuanCost: Double = 0.toDouble()
    }
}