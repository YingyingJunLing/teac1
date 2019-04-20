package com.wd.tech.mvp.view.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.widget.GridLayoutManager
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.donkingliang.imageselector.utils.ImageSelector
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.ReleasePostBean
import com.wd.tech.mvp.model.utils.AlterAndAnimationUtil
import com.wd.tech.mvp.presenter.presenterimpl.UserPushCommentPresenter
import com.wd.tech.mvp.view.adapter.UserPushImageAdapter
import com.wd.tech.mvp.view.base.BaseActivity
import com.wd.tech.mvp.view.contract.Contract
import kotlinx.android.synthetic.main.activity_user_push_comment.*
import kotlinx.android.synthetic.main.dialog_camera_layout.view.*
import java.io.File
import java.util.ArrayList


class UserPushCommentActivity : BaseActivity<Contract.IUserPushCommentView,UserPushCommentPresenter>(),Contract.IUserPushCommentView {

    var userPushCommentPresenter : UserPushCommentPresenter = UserPushCommentPresenter(this)
    var hashMap : HashMap<String,String> = HashMap()
    val PHOTO_REQUEST_CAREMA : Int = 1
    val PHOTO_REQUEST_GALLERY : Int = 2
    var alterAndAnimationUtil : AlterAndAnimationUtil = AlterAndAnimationUtil()
    private val REQUEST_CODE = 0x00000011
    lateinit var mAdapter : UserPushImageAdapter
    var list : ArrayList<String> = ArrayList<String>()

    override fun createPresenter(): UserPushCommentPresenter? {
        return userPushCommentPresenter
    }

    override fun initActivityView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_user_push_comment)
    }

    override fun initData() {
        var sharedPreferences : SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)
        var userId = sharedPreferences.getString("userId", "0")
        var sessionId = sharedPreferences.getString("sessionId", "0")
        hashMap.put("userId",userId)
        hashMap.put("sessionId",sessionId)
    }

    override fun initView() {
        val layoutParams = char_edit.layoutParams
        layoutParams.width = MainActivity.width-88
        char_edit.setLayoutParams(layoutParams)
        char_edit.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                char_num_text.setText(char_edit.text.toString().length.toString()+"/300")
            }
        })
        user_push_comment_image_recycle.layoutManager = GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false)
        mAdapter = UserPushImageAdapter(this,list)
        user_push_comment_image_recycle.adapter = mAdapter
        mAdapter.setUserPushImageOnClick(object : UserPushImageAdapter.IClickListener{
            override fun setIClickListener() {
                dialogShow()
            }
        })
        push_comment_text.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var flieList : ArrayList<File> = ArrayList<File>()
                for (i in list){
                    var file : File = File(i)
                    flieList.add(file)
                }
                userPushCommentPresenter.onIUserPushCommentPre(hashMap,char_edit.text.toString(),flieList)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        list == null
        if (requestCode == REQUEST_CODE && data != null) {
            //获取选择器返回的数据
            list = data.getStringArrayListExtra(
                ImageSelector.SELECT_RESULT
            )
            data.getBooleanExtra(ImageSelector.IS_CAMERA_IMAGE, false)

            mAdapter = UserPushImageAdapter(this,list)
            user_push_comment_image_recycle.adapter = mAdapter
            mAdapter.setUserPushImageOnClick(object : UserPushImageAdapter.IClickListener{
                override fun setIClickListener() {
                    dialogShow()
                }
            })
        }
    }

    override fun onSuccess(releasePostBean: ReleasePostBean) {
        Toast.makeText(this@UserPushCommentActivity,releasePostBean.message,Toast.LENGTH_LONG).show()
        list==null
        char_edit.setText("")
        mAdapter = UserPushImageAdapter(this,list)
        user_push_comment_image_recycle.adapter = mAdapter
    }

    override fun onFail() {

    }

    private fun dialogShow(){
        var view : View = View.inflate(this@UserPushCommentActivity,R.layout.dialog_camera_layout,null)
        alterAndAnimationUtil.AlterDialog(this@UserPushCommentActivity,view)
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
                ImageSelector.builder()
                    .useCamera(true) // 设置是否使用拍照
                    .setSingle(false)  //设置是否单选
                    .setViewImage(true) //是否点击放大图片查看,，默认为true
                    .setMaxSelectCount(0) // 图片的最大选择数量，小于等于0时，不限数量。
                    .start(this@UserPushCommentActivity, REQUEST_CODE) // 打开相册
                alterAndAnimationUtil.hideDialog()
            }
        })
        //点击取消
        view.finsh_head_linear.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                alterAndAnimationUtil.hideDialog()
            }
        })
    }
}
