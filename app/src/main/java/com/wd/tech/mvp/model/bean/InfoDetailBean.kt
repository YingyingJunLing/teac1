package com.wd.tech.mvp.model.bean

class InfoDetailBean
{

    internal var result: ResultBean? = null
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

        var comment: Int = 0
        var content: String? = null
        var id: Int = 0
        var integralCost: Int = 0
        var praise: Int = 0
        var readPower: Int = 0
        var relatedArticles: String? = null
        var releaseTime: Long = 0
        var share: Int = 0
        var source: String? = null
        var summary: String? = null
        var thumbnail: String? = null
        var title: String? = null
        var whetherCollection: Int = 0
        var whetherGreat: Int = 0
        var yuanCost: Int = 0
        var informationList: List<InformationListBean>? = null
        var plate: List<PlateBean>? = null

        class InformationListBean {

            var id: Int = 0
            var thumbnail: String? = null
            var title: String? = null
        }

        class PlateBean {
            /**
             * id : 1
             * name : 电商消费
             */

            var id: Int = 0
            var name: String? = null
        }
    }
}