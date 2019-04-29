package com.wd.tech.mvp.view.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import cn.jpush.im.android.api.JMessageClient
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.ModifyHeadPicBean
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

    var settingUserInfoPresenter : SettingUserInfoPresenter = SettingUserInfoPresenter(this)
    var hashMap : HashMap<String,String> = HashMap()
    val PHOTO_REQUEST_CAREMA : Int = 1
    val PHOTO_REQUEST_GALLERY : Int = 2
    var alterAndAnimationUtil : AlterAndAnimationUtil = AlterAndAnimationUtil()
    lateinit var status : String

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
            user_sign_text.setText(userInfoBean.result.signature)
            user_bir_text.setText("")
            user_tel_text.setText(userInfoBean.result.phone)
            user_emile_text.setText(userInfoBean.result.email)
            user_num_text.setText(userInfoBean.result.integral.toString())
            user_vip_text.setText(userInfoBean.result.whetherVip.toString())
            Face_ID_text.setText("")
            EventBus.getDefault().postSticky(userInfoBean)
            //点击绑定faceid
            fecaid_lin.setOnClickListener(object :View.OnClickListener{
                override fun onClick(v: View?) {
                    var view : View = View.inflate(this@MySettingActivity,R.layout.dialog_reg_face_layout,null)
                    var bulider : AlertDialog.Builder = AlertDialog.Builder(this@MySettingActivity)
                    var alertDialog = bulider.create()
                    alertDialog.setView(view)
                    alertDialog.setCanceledOnTouchOutside(true)
                    alertDialog.show()
                  view.btn_register.setOnClickListener(object :View.OnClickListener{
                        override fun onClick(v: View?) {

                        }
                    })
                    view.btn_cancel.setOnClickListener(object :View.OnClickListener{
                        override fun onClick(v: View?) {
                            alertDialog.dismiss()
                        }
                    })
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
            user_vip_text.setText("")
            Face_ID_text.setText("")
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
            }else {
                Toast.makeText(this, "未找到存储啦，无法存储照片", Toast.LENGTH_SHORT).show()
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
            // myHead.setImageBitmap(bitmap)
            settingUserInfoPresenter.onModifyHeadPicPre(hashMap,file)
        }else {
            Toast.makeText(this, "未找到存储啦，无法存储照片", Toast.LENGTH_SHORT).show()
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
}
