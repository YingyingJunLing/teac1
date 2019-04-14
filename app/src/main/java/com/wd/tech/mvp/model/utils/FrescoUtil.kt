package com.wd.tech.mvp.model.utils

import android.net.Uri
import com.facebook.drawee.view.SimpleDraweeView

class FrescoUtil {
    companion object{
        public fun setPic(uri : String, simpleDraweeView : SimpleDraweeView){
            var url : Uri = Uri.parse(uri)
            simpleDraweeView.setImageURI(url,this)
        }
    }
}