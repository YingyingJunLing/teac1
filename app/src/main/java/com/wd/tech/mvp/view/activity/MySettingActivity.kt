package com.wd.tech.mvp.view.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.UserInfoBean
import com.wd.tech.mvp.model.bean.UserSignBean
import com.wd.tech.mvp.model.utils.AlterAndAnimationUtil
import com.wd.tech.mvp.model.utils.FrescoUtil
import com.wd.tech.mvp.presenter.presenterimpl.UserInfoPresenter
import com.wd.tech.mvp.view.base.BaseActivity
import com.wd.tech.mvp.view.contract.Contract
import kotlinx.android.synthetic.main.activity_my_setting.*
import kotlinx.android.synthetic.main.dialog_camera_layout.view.*

class MySettingActivity : BaseActivity<Contract.IUserInfoView, UserInfoPresenter>(), Contract.IUserInfoView {

    var userInfoPresenter : UserInfoPresenter = UserInfoPresenter(null,this)
    var hashMap : HashMap<String,String> = HashMap()

    override fun createPresenter(): UserInfoPresenter? {
        return userInfoPresenter
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
        userInfoPresenter.onIUserInfoPre(hashMap)
    }

    override fun initView() {

    }

    override fun onSuccess(userInfoBean: UserInfoBean) {
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
        user_head_linear.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var view : View = View.inflate(this@MySettingActivity,R.layout.dialog_camera_layout,null)
                var alterAndAnimationUtil : AlterAndAnimationUtil = AlterAndAnimationUtil()
                alterAndAnimationUtil.AlterDialog(this@MySettingActivity,view)
                //点击拍摄
                view.film_head_linear.setOnClickListener(object : View.OnClickListener{
                    override fun onClick(v: View?) {
                        var intentFilm : Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
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

    override fun onSignSuccess(userSignBean: UserSignBean) {

    }

    override fun onFail() {

    }

}
