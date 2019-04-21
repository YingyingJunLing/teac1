package com.wd.tech.mvp.model.bean

class FindAllInfoPlate
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
        var id: Int = 0
        var name: String? = null
        var pic: String? = null
    }
}