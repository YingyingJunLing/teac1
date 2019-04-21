package com.wd.tech.mvp.model.bean

class BySourceBean
{
    private var message: String? = null
    private var status: String? = null
    private var result: List<ResultBean>? = null

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

    fun getResult(): List<ResultBean>? {
        return result
    }

    fun setResult(result: List<ResultBean>) {
        this.result = result
    }

    class ResultBean {
        /**
         * id : 4
         * releaseTime : 1535449854000
         * source : 砍柴网
         * title : 最后的红利：三四五线网民时间和金钱消费报告
         */

        var id: Int = 0
        var releaseTime: Long = 0
        var source: String? = null
        var title: String? = null
    }
}