package com.wd.tech.mvp.model.utils

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.wd.tech.R

class AlterAndAnimationUtil {
    private var dialog : Dialog ?= null
    public fun AlterDialog(context: Context,view: View){
        dialog = Dialog(context, R.style.ActionSheetDialogStyle)
        //填充对话框的布局
        //将布局设置给Dialog
        dialog!!.setContentView(view)
        //获取当前Activity所在的窗体
        var dialogWindow : Window ?= dialog!!.window
        //设置Dialog从窗体底部弹出
        dialogWindow!!.setGravity(Gravity.BOTTOM)
        var lp : WindowManager.LayoutParams = dialogWindow.attributes
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.MATCH_PARENT
        //将属性设置给窗体
        dialogWindow.attributes = lp
        dialog!!.setCanceledOnTouchOutside(true)
        dialog!!.show()
        dialog!!.setCancelable(false)
    }

    public fun hideDialog(){
        dialog!!.dismiss()
    }
}