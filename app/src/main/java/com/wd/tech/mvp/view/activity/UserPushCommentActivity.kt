package com.wd.tech.mvp.view.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.donkingliang.imageselector.utils.ImageSelector
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.ReleasePostBean
import com.wd.tech.mvp.model.utils.AlterAndAnimationUtil
import com.wd.tech.mvp.presenter.presenterimpl.UserPushCommentPresenter
import com.wd.tech.mvp.view.base.BaseActivity
import com.wd.tech.mvp.view.contract.Contract
import kotlinx.android.synthetic.main.activity_user_push_comment.*
import kotlinx.android.synthetic.main.dialog_camera_layout.view.*



class UserPushCommentActivity : BaseActivity<Contract.IUserPushCommentView,UserPushCommentPresenter>(),Contract.IUserPushCommentView {

    var userPushCommentPresenter : UserPushCommentPresenter = UserPushCommentPresenter(this)
    var hashMap : HashMap<String,String> = HashMap()
    val PHOTO_REQUEST_CAREMA : Int = 1
    val PHOTO_REQUEST_GALLERY : Int = 2
    var alterAndAnimationUtil : AlterAndAnimationUtil = AlterAndAnimationUtil()
    private val REQUEST_CODE = 0x00000011

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
        /*user_push_comment_image_recycle.layoutManager = GridLayoutManager(this,GridLayoutManager.VERTICAL,3,false)*/
        /*user_push_comment_image_recycle.adapter = UserPushImageAdapter()*/
        add_image.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
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
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && data != null) {
            //获取选择器返回的数据
            val images = data.getStringArrayListExtra(
                ImageSelector.SELECT_RESULT
            )

            /**
             * 是否是来自于相机拍照的图片，
             * 只有本次调用相机拍出来的照片，返回时才为true。
             * 当为true时，图片返回的结果有且只有一张图片。
             */
            val isCameraImage = data.getBooleanExtra(ImageSelector.IS_CAMERA_IMAGE, false)
        }
    }

    override fun onSuccess(releasePostBean: ReleasePostBean) {

    }

    override fun onFail() {

    }
}
