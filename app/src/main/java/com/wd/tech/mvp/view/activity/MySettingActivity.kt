package com.wd.tech.mvp.view.activity

import android.app.AlertDialog
import android.content.*
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import cn.jpush.im.android.api.JMessageClient
import com.wd.tech.R
import com.wd.tech.mvp.model.app.MyApp
import com.wd.tech.mvp.model.bean.ModifyHeadPicBean
import com.wd.tech.mvp.model.bean.UntiedFaceIdBean
import com.wd.tech.mvp.model.bean.UserInfoBean
import com.wd.tech.mvp.model.utils.AlterAndAnimationUtil
import com.wd.tech.mvp.model.utils.FrescoUtil
import com.wd.tech.mvp.presenter.presenterimpl.SettingUserInfoPresenter
import com.wd.tech.mvp.view.base.BaseActivity
import com.wd.tech.mvp.view.contract.Contract
import kotlinx.android.synthetic.main.activity_my_setting.*
import kotlinx.android.synthetic.main.dialog_camera_layout.view.*
import kotlinx.android.synthetic.main.dialog_end_login_layout.view.*
import kotlinx.android.synthetic.main.dialog_reg_face_layout.*
import kotlinx.android.synthetic.main.dialog_reg_face_layout.view.*
import org.greenrobot.eventbus.EventBus
import java.io.File
import java.util.ArrayList

class MySettingActivity : BaseActivity<Contract.ISettingUserInfoView, SettingUserInfoPresenter>(), Contract.ISettingUserInfoView {
    override fun onUntiedFaceIdSuccess(any: Any) {
        if(any is UntiedFaceIdBean)
        {
            Toast.makeText(this@MySettingActivity,any.message,Toast.LENGTH_LONG).show()
        }
    }

    var settingUserInfoPresenter : SettingUserInfoPresenter = SettingUserInfoPresenter(this)
    var hashMap : HashMap<String,String> = HashMap()
    val PHOTO_REQUEST_CAREMA : Int = 1
    val PHOTO_REQUEST_GALLERY : Int = 2
    var alterAndAnimationUtil : AlterAndAnimationUtil = AlterAndAnimationUtil()
    lateinit var status : String
    val REQUEST_CODE_IMAGE_CAMERA = 1
    val REQUEST_CODE_IMAGE_OP = 2
    val REQUEST_CODE_OP = 3
    val REQUEST_CODE_IMAGE_CAMERA_FACE=4
    val REQUEST_CODE_IMAGE_OP_FACE=5

    override fun createPresenter(): SettingUserInfoPresenter? {
        return settingUserInfoPresenter
    }

    override fun initActivityView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_my_setting)
    }

    override fun initData() {
        var sharedPreferences : SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)
        var userId = sharedPreferences.getString("userId", "0")
        var sessionId = sharedPreferences.getString("sessionId", "0")
        hashMap.put("userId",userId)
        hashMap.put("sessionId",sessionId)
        if (userId != "0" && sessionId != "0"){
            settingUserInfoPresenter.onSettingIUserInfoPre(hashMap)
        }
    }

    override fun initView() {
        image_finsh.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                finish()
            }
        })
        user_head_linear.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                if (status=="0000"){
                    var view : View = View.inflate(this@MySettingActivity,R.layout.dialog_camera_layout,null)
                    alterAndAnimationUtil.AlterDialog(this@MySettingActivity,view)
                    //点击拍摄
                    view.film_head_linear.setOnClickListener(object : View.OnClickListener{
                        override fun onClick(v: View?) {
                            var intentFilm : Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            startActivityForResult(intentFilm,PHOTO_REQUEST_CAREMA)
                            alterAndAnimationUtil.hideDialog()
                        }
                    })
                    //相册选择
                    view.photo_head_linear.setOnClickListener(object : View.OnClickListener{
                        override fun onClick(v: View?) {
                            var intent_choosePhoto : Intent = Intent(Intent.ACTION_PICK)
                            intent_choosePhoto.setType("image/*")
                            startActivityForResult(intent_choosePhoto, PHOTO_REQUEST_GALLERY)
                            alterAndAnimationUtil.hideDialog()
                        }
                    })
                    //点击取消
                    view.finsh_head_linear.setOnClickListener(object : View.OnClickListener{
                        override fun onClick(v: View?) {
                            alterAndAnimationUtil.hideDialog()
                        }
                    })
                }else{
                    Toast.makeText(this@MySettingActivity,"请先登录",Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onSuccess(userInfoBean: UserInfoBean) {
        status = userInfoBean.status
        if (userInfoBean.status=="0000"){
            FrescoUtil.setPic(userInfoBean.result.headPic,user_head_simple)
            user_name_text.setText(userInfoBean.result.nickName)
            if (userInfoBean.result.sex == 1){
                user_sex_text.setText("男")
            }else{
                user_sex_text.setText("女")
            }
            if(userInfoBean.result.whetherFaceId == 1)
            {
                Face_ID_text.text="已绑定"
            }else{
                Face_ID_text.text="点击绑定"
            }

            if(userInfoBean.result.whetherVip == 2)
            {
                user_vip_text.setText("非会员")
            }else{
                user_vip_text.setText("会员")
            }
            user_sign_text.setText(userInfoBean.result.signature)
            user_bir_text.setText("")
            user_tel_text.setText(userInfoBean.result.phone)
            user_emile_text.setText(userInfoBean.result.email)
            user_num_text.setText(userInfoBean.result.integral.toString())


            EventBus.getDefault().postSticky(userInfoBean)
            //点击绑定faceid
            fecaid_lin.setOnClickListener(object :View.OnClickListener{
                override fun onClick(v: View?) {
                 if(Face_ID_text.text.toString().equals("点击绑定"))
                 {
                     AlertDialog.Builder(this@MySettingActivity)
                         .setTitle("请选择注册方式")
                         .setIcon(android.R.drawable.ic_dialog_info)
                         .setItems(arrayOf("打开图片", "拍摄照片")) { dialog, which ->
                             when (which) {
                                 1 -> {
                                     val intent = Intent("android.media.action.IMAGE_CAPTURE")
                                     val values = ContentValues(1)
                                     values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                                     val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
                                     MyApp.setCaptureImage(uri)
                                     intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
                                     startActivityForResult(intent, REQUEST_CODE_IMAGE_CAMERA_FACE)
                                 }
                                 0 -> {
                                     val getImageByalbum = Intent(Intent.ACTION_GET_CONTENT)
                                     getImageByalbum.addCategory(Intent.CATEGORY_OPENABLE)
                                     getImageByalbum.type = "image/jpeg"
                                     startActivityForResult(getImageByalbum, REQUEST_CODE_IMAGE_OP_FACE)
                                 }
                                 else -> {
                                 }
                             }
                         }
                         .show()
                 } else if (Face_ID_text.text.toString() == "已绑定") {
                     val builder1 = AlertDialog.Builder(this@MySettingActivity)
                     //    设置Title的内容
                     builder1.setTitle("温馨提示")
                     //    设置Content来显示一个信息
                     builder1.setMessage("确定取消绑定吗？")
                     //    设置一个PositiveButton
                     builder1.setPositiveButton("取消绑定") { dialog, which ->
                        settingUserInfoPresenter.onUntiedFaceId(hashMap)
                         Face_ID_text.text = "点击绑定"
                     }
                     //设置一个NegativeButton
                     builder1.setNegativeButton("再想想", null)
                     //    显示出该对话框
                     builder1.show()
                 }
                }
            })
            end_login_linear.setOnClickListener(object : View.OnClickListener{
                override fun onClick(v: View?) {
                    var view : View = View.inflate(this@MySettingActivity,R.layout.dialog_end_login_layout,null)
                    var bulider : AlertDialog.Builder = AlertDialog.Builder(this@MySettingActivity)
                    var alertDialog = bulider.create()
                    alertDialog.setView(view)
                    alertDialog.setCanceledOnTouchOutside(true)
                    alertDialog.show()
                    view.end_login_true_text.setOnClickListener(object : View.OnClickListener{
                        override fun onClick(v: View?) {
                            var sharedPreferences : SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)
                            sharedPreferences.edit().putString("userId", "0").putString("sessionId", "0").commit()
                            var userId = sharedPreferences.getString("userId", "0")
                            var sessionId = sharedPreferences.getString("sessionId", "0")
                            var hashMapEnd : HashMap<String,String> = HashMap()
                            hashMapEnd.put("userId",userId)
                            hashMapEnd.put("sessionId",sessionId)
                            JMessageClient.logout();
                            alertDialog.dismiss()
                            settingUserInfoPresenter.onSettingIUserInfoPre(hashMapEnd)
                        }
                    })
                    view.end_login_false_text.setOnClickListener(object : View.OnClickListener{
                        override fun onClick(v: View?) {
                            alertDialog.dismiss()
                        }
                    })
                }
            })
        }else{
            Toast.makeText(this,userInfoBean.message,Toast.LENGTH_LONG).show()
            end_login_linear.visibility = View.GONE
            FrescoUtil.setPic("",user_head_simple)
            user_name_text.setText("")
            user_sex_text.setText("")
            user_sign_text.setText("")
            user_bir_text.setText("")
            user_tel_text.setText("")
            user_emile_text.setText("")
            user_num_text.setText("")
        }
    }

    override fun onFail() {

    }

    public fun hasSdcard() : Boolean{
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            return true
        }else{
            return false
        }
    }

    override fun onModifyHeadPicSuccess(modifyHeadPicBean: ModifyHeadPicBean) {
        Toast.makeText(this, modifyHeadPicBean.message, Toast.LENGTH_SHORT).show()
        settingUserInfoPresenter.onSettingIUserInfoPre(hashMap)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==PHOTO_REQUEST_CAREMA&&resultCode==RESULT_OK){
            if (hasSdcard()){
                val bitmap : Bitmap = data!!.getParcelableExtra("data")
                //将bitmap转换为uri
                var uri : Uri = Uri.parse(MediaStore.Images.Media.insertImage(this.contentResolver,bitmap,null,null))
                val proj = arrayOf(MediaStore.Images.Media.DATA)
                var actualimagecursor = contentResolver.query(uri,proj,null,null,null)
                var actual_image_column_index : Int = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                actualimagecursor.moveToFirst()
                var img_path : String = actualimagecursor.getString(actual_image_column_index)
                val file = File(img_path)
                val list = ArrayList<File>()
                list.add(file)
                // myHead.setImageBitmap(bitmap)
                settingUserInfoPresenter.onModifyHeadPicPre(hashMap,file)
            }
        }else if (requestCode==PHOTO_REQUEST_GALLERY&&resultCode==RESULT_OK){
            val uri = data?.getData()
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            var actualimagecursor = contentResolver.query(uri,proj,null,null,null)
            var actual_image_column_index : Int = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            actualimagecursor.moveToFirst()
            var img_path : String = actualimagecursor.getString(actual_image_column_index)
            val file = File(img_path)
            val list = ArrayList<File>()
            list.add(file)
            settingUserInfoPresenter.onModifyHeadPicPre(hashMap,file)
        }
        if (requestCode == REQUEST_CODE_IMAGE_OP_FACE && resultCode == RESULT_OK) {
            val mPath = data!!.getData()
            val file = getPath(mPath)
            val bmp = MyApp.decodeImage(file!!)
            if (bmp == null || bmp!!.getWidth() <= 0 || bmp!!.getHeight() <= 0) {
                Log.e("SettingActivity", "error")
            } else {
                Log.i("SettingActivity", "bmp [" + bmp!!.getWidth() + "," + bmp!!.getHeight())
            }
            startRegister(bmp!!, file!!)
        } else if (requestCode == REQUEST_CODE_IMAGE_OP_FACE) {
            //  Log.i(TAG, "RESULT =$resultCode")

            if (data == null) {
                return
            }
            val bundle = data.extras
            val path = bundle!!.getString("imagePath")
        } else if (requestCode == REQUEST_CODE_IMAGE_CAMERA_FACE && resultCode == RESULT_OK) {
            val mPath = MyApp.getCaptureImage()
            val file = getPath(mPath)
            val bmp = MyApp.decodeImage(file!!)
            startRegister(bmp!!, file!!)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (AlterAndAnimationUtil.type=="1"){

            }else{
                finish()
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        super.onDestroy()
        settingUserInfoPresenter.detachView()
    }
    //人脸的设置
    fun getPath(uri: Uri): String? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (DocumentsContract.isDocumentUri(this, uri)) {
                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    val type = split[0]

                    if ("primary".equals(type, ignoreCase = true)) {
                        return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                    }
                } else if (isDownloadsDocument(uri)) {

                    val id = DocumentsContract.getDocumentId(uri)
                    val contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id)
                    )

                    return getDataColumn(this, contentUri, null, null)
                } else if (isMediaDocument(uri)) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    val type = split[0]

                    var contentUri: Uri? = null
                    if ("image" == type) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    } else if ("video" == type) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    } else if ("audio" == type) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    }

                    val selection = "_id=?"
                    val selectionArgs = arrayOf(split[1])

                    return getDataColumn(this, contentUri, selection, selectionArgs)
                }
            }
        }
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val actualimagecursor = this.contentResolver.query(uri, proj, null, null, null)
        val actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        actualimagecursor.moveToFirst()
        val img_path = actualimagecursor.getString(actual_image_column_index)
        val end = img_path.substring(img_path.length - 4)
        return if (0 != end.compareTo(".jpg", ignoreCase = true) && 0 != end.compareTo(".png", ignoreCase = true)) {
            null
        } else img_path
    }
    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    fun getDataColumn(
        context: Context, uri: Uri?, selection: String?,
        selectionArgs: Array<String>?
    ): String? {

        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(column)

        try {
            cursor = context.contentResolver.query(uri!!, projection, selection, selectionArgs, null)
            if (cursor != null && cursor.moveToFirst()) {
                val index = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(index)
            }
        } finally {
            cursor?.close()
        }
        return null
    }

    /**
     * @param mBitmap
     */
    fun startRegister(mBitmap: Bitmap, file: String) {
        val it = Intent(this@MySettingActivity, PeopleActivity::class.java)
        val bundle = Bundle()
        bundle.putString("imagePath", file)
        it.putExtras(bundle)
        startActivityForResult(it, REQUEST_CODE_OP)
    }

    override fun onResume() {
        super.onResume()
        settingUserInfoPresenter.onSettingIUserInfoPre(hashMap)
        Face_ID_text.text="已绑定"
    }
}
