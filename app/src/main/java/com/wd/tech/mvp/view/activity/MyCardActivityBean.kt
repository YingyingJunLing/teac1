package com.wd.tech.mvp.view.activity

class MyCardActivityBean
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


        var comment: Int = 0
        var content: String? = null
        var file: String? = null
        var headPic: String? = null
        var id: Int = 0
        var nickName: String? = null
        var praise: Int = 0
        var publishTime: Long = 0
        var signature: String? = null
        var userId: Int = 0
    }
}