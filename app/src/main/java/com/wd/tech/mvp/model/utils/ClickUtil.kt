package com.wd.tech.mvp.model.utils

class ClickUtil {
    //恶意点击
    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    companion object {
        val MIN_CLICK_DELAY_TIME : Int = 1000
        var lastClickTime : Long = 0

        fun isFastClick() : Boolean{
            var flag : Boolean = false
            var curClickTime : Long = System.currentTimeMillis()
            if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME){
                flag = true
            }
            lastClickTime = curClickTime
            return flag
        }
    }
}