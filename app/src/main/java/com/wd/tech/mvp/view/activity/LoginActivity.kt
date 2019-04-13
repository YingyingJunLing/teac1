package com.wd.tech.mvp.view.activity


import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.wd.tech.R
import com.wd.tech.base.RsaCoder
import com.wd.tech.mvp.model.bean.LoginBean
import com.wd.tech.mvp.model.utils.AccountValidatorUtil
import com.wd.tech.mvp.presenter.base.LoginPresenter
import com.wd.tech.mvp.view.base.BaseActivity
import com.wd.tech.mvp.view.contract.Contract
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : BaseActivity<Contract.ILoginView,LoginPresenter>(),Contract.ILoginView, View.OnClickListener {

    var loginPresenter :LoginPresenter?=null
    var accountValidatorUtil:AccountValidatorUtil?=null

    override fun createPresenter(): LoginPresenter? {
         loginPresenter = LoginPresenter(this);
        return loginPresenter
    }

    override fun initActivityView(savedInstanceState: Bundle?)
    {
        setContentView(R.layout.activity_login)
    }

    override fun initData() {

    }

    override fun initView() {
        login_btn.setOnClickListener(this)
        login_reg.setOnClickListener(this)
        login_pwd_eye.setOnClickListener(this)
        accountValidatorUtil = AccountValidatorUtil()

    }

    override fun onSuccess(loginBean: LoginBean)
    {

        if(loginBean.status.equals("0000"))
        {
            Toast.makeText(this,loginBean.message,Toast.LENGTH_LONG).show()
        }
    }

    override fun onFail() {

    }
    override fun onClick(v: View?) {
        when(v!!.id)
        {
            //登录按钮点击事件
            R.id.login_btn ->{
                var login_pwd =  login_pwd.text.toString().trim()
                //加密后的密码
                var passWord = RsaCoder.encryptByPublicKey(login_pwd)

                var login_phone = login_phone.text.toString().trim()
                //判断手机号是否合法
                val mobile = accountValidatorUtil!!.isMobile(login_phone)
                if(!mobile)
                {
                    Toast.makeText(this,"输入数据不合法，请检查",Toast.LENGTH_LONG).show()
                    return
                }else{
                    loginPresenter!!.onILoinPre(login_phone,passWord)
                }

            }

        }

    }
}
