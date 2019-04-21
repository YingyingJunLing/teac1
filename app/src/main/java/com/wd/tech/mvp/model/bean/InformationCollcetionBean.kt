package com.wd.tech.mvp.model.bean

class InformationCollcetionBean
{
    private var message: String? = null
    private var status: String? = null

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
}