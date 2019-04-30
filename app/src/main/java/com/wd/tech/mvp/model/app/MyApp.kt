package com.wd.tech.mvp.model.app

import android.app.Application
import android.content.Context
import android.content.IntentFilter
import cn.jpush.im.android.api.JMessageClient

import com.facebook.drawee.backends.pipeline.Fresco
import com.mob.MobSDK

import com.wd.tech.mvp.model.utils.NetworkChangeReceiverUtil
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.graphics.Bitmap
import android.media.ExifInterface
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.util.Log
import com.wd.tech.face.FaceDB




class MyApp : Application() {
    private val TAG = this.javaClass.toString()
    var mFaceDB: FaceDB?=null
    internal var mImage: Uri? = null
    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        var intentFilter : IntentFilter = IntentFilter()
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        var networkChangeReceiverUtil : NetworkChangeReceiverUtil = NetworkChangeReceiverUtil()
        registerReceiver(networkChangeReceiverUtil,intentFilter)
        JMessageClient.setDebugMode(true)
        JMessageClient.init(this)
        MobSDK.init(this,"2ae99603e8708","47d7cae6cfff51929a3a7cc201f7f769")
        mFaceDB = FaceDB(this.externalCacheDir!!.path)
        mImage = null
    }

    fun setCaptureImage(uri: Uri) {
        mImage = uri
    }

    fun getCaptureImage(): Uri? {
        return mImage
    }

    /**
     * @param path
     * @return
     */
    fun decodeImage(path: String): Bitmap? {
        val res: Bitmap
        try {
            val exif = ExifInterface(path)
            val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

            val op = BitmapFactory.Options()
            op.inSampleSize = 1
            op.inJustDecodeBounds = false
            //op.inMutable = true;
            res = BitmapFactory.decodeFile(path, op)
            //rotate and scale.
            val matrix = Matrix()

            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                matrix.postRotate(90f)
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                matrix.postRotate(180f)
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                matrix.postRotate(270f)
            }

            val temp = Bitmap.createBitmap(res, 0, 0, res.width, res.height, matrix, true)
            Log.d("com.arcsoft", "check target Image:" + temp.width + "X" + temp.height)

            if (temp != res) {
                res.recycle()
            }
            return temp
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }
    companion object {
        var mImage: Uri? = null
        //绘制页面时参照的设计图宽度
        val DESIGN_WIDTH = 750f
        var application: Context? = null
            private set




        fun decodeImage(path: String): Bitmap? {
            val res: Bitmap
            try {
                val exif = ExifInterface(path)
                val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

                val op = BitmapFactory.Options()
                op.inSampleSize = 1
                op.inJustDecodeBounds = false
                //op.inMutable = true;
                res = BitmapFactory.decodeFile(path, op)
                //rotate and scale.
                val matrix = Matrix()

                if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                    matrix.postRotate(90f)
                } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                    matrix.postRotate(180f)
                } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                    matrix.postRotate(270f)
                }

                val temp = Bitmap.createBitmap(res, 0, 0, res.width, res.height, matrix, true)
                Log.d("com.arcsoft", "check target Image:" + temp.width + "X" + temp.height)

                if (temp != res) {
                    res.recycle()
                }
                return temp
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return null
        }

        fun setCaptureImage(uri: Uri) {
            mImage = uri
        }

        fun getCaptureImage(): Uri {
            return mImage!!
        }
    }



}