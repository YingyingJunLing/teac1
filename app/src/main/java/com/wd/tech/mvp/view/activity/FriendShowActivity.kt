package com.wd.tech.mvp.view.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.AddFriendBean
import com.wd.tech.mvp.model.bean.FindUserByPhoneBean
import com.wd.tech.mvp.model.bean.FriendInfoMationBean
import com.wd.tech.mvp.model.utils.FrescoUtil
import com.wd.tech.mvp.presenter.presenterimpl.FriendShowPresenter
import com.wd.tech.mvp.view.base.BaseActivity
import com.wd.tech.mvp.view.contract.Contract
import kotlinx.android.synthetic.main.activity_friend_show.*
import java.util.HashMap

class FriendShowActivity : BaseActivity<Contract.IFriendShowView,FriendShowPresenter>(),Contract.IFriendShowView {

    var friendShowPresenter : FriendShowPresenter = FriendShowPresenter(this)
    var hashMap: HashMap<String, String> = HashMap()
    lateinit var phone : String

    override fun createPresenter(): FriendShowPresenter? {
        return friendShowPresenter
    }

    override fun initActivityView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_friend_show)
    }

    override fun initData() {
        var sharedPreferences: SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)
        var userId = sharedPreferences.getString("userId", "0")
        var sessionId = sharedPreferences.getString("sessionId", "0")
        hashMap.put("userId", userId)
        hashMap.put("sessionId", sessionId)
        phone = intent.getStringExtra("phone")
        if (phone.length==11){
            friendShowPresenter.onIFriendPhoneShowPre(hashMap,phone)
        }else{
            friendShowPresenter.onIFriendIdShowPre(hashMap,phone)
        }
    }

    override fun initView() {

    }

    override fun onPhoneSuccess(findUserByPhoneBean: FindUserByPhoneBean) {
        if (findUserByPhoneBean.status=="0000"){
            friendShowPresenter.onIFriendIdShowPre(hashMap,findUserByPhoneBean.result.userId.toString())
        }else{
            Toast.makeText(this@FriendShowActivity,findUserByPhoneBean.message,Toast.LENGTH_LONG).show()
        }
    }

    override fun onIdSuccess(friendInfoMationBean: FriendInfoMationBean) {
        if (friendInfoMationBean.status=="0000"){
            find_friend_nick.setText(friendInfoMationBean.result.nickName)
            FrescoUtil.setPic(friendInfoMationBean.result.headPic,find_friend_img)
            find_friend_sign.setText("")
            btn_find_friend.setOnClickListener(object : View.OnClickListener{
                override fun onClick(v: View?) {
                    friendShowPresenter.onIFriendAddShowPre(hashMap,friendInfoMationBean.result.userId.toString(),"约吗？")
                }
            })
        }else{
            Toast.makeText(this@FriendShowActivity,friendInfoMationBean.message,Toast.LENGTH_LONG).show()
        }
    }

    override fun onAddSuccess(addFriendBean: AddFriendBean) {
        Toast.makeText(this@FriendShowActivity,addFriendBean.message,Toast.LENGTH_LONG).show()
    }

    override fun onFail() {

    }
}
