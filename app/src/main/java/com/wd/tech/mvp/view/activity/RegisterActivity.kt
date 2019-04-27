package com.wd.tech.mvp.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.wd.tech.R
import com.wd.tech.base.RsaCoder
import com.wd.tech.mvp.model.bean.RegBean
import com.wd.tech.mvp.model.utils.AccountValidatorUtil
import com.wd.tech.mvp.presenter.presenterimpl.RegPresenter
import com.wd.tech.mvp.view.base.BaseActivity
import com.wd.tech.mvp.view.contract.Contract
import kotlinx.android.synthetic.main.activity_register.*
import cn.smssdk.SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE
import cn.smssdk.SMSSDK.RESULT_COMPLETE

import android.os.Handler
import android.os.Message
import cn.smssdk.SMSSDK


class RegisterActivity : BaseActivity<Contract.IRegView, RegPresenter>(), Contract.IRegView, View.OnClickListener {

    var regPresenter: RegPresenter? = null
    var accountValidatorUtil: AccountValidatorUtil? = null
    var phone : String ?= null
    var passWord : String ?= null
    var pwd : String ?= null
    private val hander = object : Handler() {
        override fun handleMessage(msg: Message) {
            val event = msg.arg1
            val result = msg.arg2
            val data = msg.obj
            if (result == SMSSDK.RESULT_COMPLETE) {
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //right
                    Toast.makeText(this@RegisterActivity, "验证成功", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@RegisterActivity, "验证码错误", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun createPresenter(): RegPresenter? {
        regPresenter = RegPresenter(this)
        return regPresenter
    }

    override fun initActivityView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_register)
        accountValidatorUtil = AccountValidatorUtil()
    }

    override fun initData() {

    }

    override fun initView() {
        reg_btn.setOnClickListener(this)


    }

    override fun onSuccess(regBean: RegBean) {
        if (regBean.status.equals("0000")) {
            Toast.makeText(this, regBean.message, Toast.LENGTH_LONG).show()
            startActivity(Intent(this@RegisterActivity,LoginActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, regBean.message, Toast.LENGTH_LONG).show()
        }

    }

    override fun onFail() {

    }

    //按钮点击事件
    override fun onClick(v: View?) {
        when (v!!.id) {
            //注册按钮
            R.id.reg_btn -> {

                var reg_nickName = reg_nickName.text.toString().trim()
                //验证手机号
                phone = reg_phone.text.toString().trim()
                val mobile = accountValidatorUtil!!.isMobile(phone!!)
                if (!mobile) {
                    Toast.makeText(this, "输入的手机号不合法", Toast.LENGTH_LONG).show()
                }
                //验证密码
                pwd = reg_pwd.text.toString().trim()
                passWord = RsaCoder.encryptByPublicKey(pwd)
                regPresenter!!.onIRegPre(phone!!,reg_nickName,passWord!!)
            }
            //短信验证码按钮点击事件
            R.id.login_reg_message -> {

            }
        }
    }
}
