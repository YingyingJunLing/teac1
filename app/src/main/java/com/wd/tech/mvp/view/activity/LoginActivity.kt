package com.wd.tech.mvp.view.activity


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.wd.tech.R
import com.wd.tech.base.RsaCoder
import com.wd.tech.mvp.model.bean.LoginBean
import com.wd.tech.mvp.model.utils.AccountValidatorUtil
import com.wd.tech.mvp.presenter.presenterimpl.LoginPresenter
import com.wd.tech.mvp.view.base.BaseActivity
import com.wd.tech.mvp.view.contract.Contract
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : BaseActivity<Contract.ILoginView, LoginPresenter>(),Contract.ILoginView, View.OnClickListener {
    var wxapi: IWXAPI? = null
    var loginPresenter : LoginPresenter?=null
    var accountValidatorUtil:AccountValidatorUtil?=null
    private var sp: SharedPreferences? = null
    var b : Boolean = false

    override fun createPresenter(): LoginPresenter? {
         loginPresenter = LoginPresenter(this);
        return loginPresenter
    }
    override fun initActivityView(savedInstanceState: Bundle?){
        setContentView(R.layout.activity_login)
        //通过WXAPIFactory工厂获取IWXApI的示例
        wxapi = WXAPIFactory.createWXAPI(this, "wxb3852e6a6b7d9516", true)
        //将应用的appid注册到微信
        wxapi!!.registerApp("wx4c96b6b8da494224")
    }

    override fun initData() {
        b = sp!!.getBoolean("记住", false)
        if (b) {
            login_phone.setText(sp!!.getString("name", ""))
            login_pwd.setText(sp!!.getString("pass", ""))
            login_remember_pwd.setChecked(b)
        }
    }

    override fun initView() {
        login_btn.setOnClickListener(this)
        login_reg.setOnClickListener(this)
        login_pwd_eye.setOnClickListener(this)
        accountValidatorUtil = AccountValidatorUtil()
        sp = getSharedPreferences("LOGIN", Context.MODE_PRIVATE)
    }

    override fun onSuccess(loginBean: LoginBean){

        if(loginBean.status.equals("0000")) {
            val sp = getSharedPreferences("User", Context.MODE_PRIVATE)
            sp.edit().putString("userId", loginBean.result.userId).putString("sessionId", loginBean.result.sessionId).commit()
            if(login_remember_pwd.isChecked){
                sp.edit().putString("type","1").commit()
            }else{
                sp.edit().putString("type","2").commit()
            }
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            intent.putExtra("first","1")
            startActivity(intent)
            Toast.makeText(this,loginBean.message,Toast.LENGTH_LONG).show()
            finish()
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
                //验证密码
                val password = accountValidatorUtil!!.isPassword(login_pwd)
                if (!password) {
                    Toast.makeText(this, "输入密码不合法", Toast.LENGTH_LONG).show()
                }
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
                //记住密码
                val edit = sp!!.edit()
                edit.putString("name", login_phone)
                edit.putString("pass", login_pwd)
                edit.putBoolean("记住", login_remember_pwd.isChecked())
                edit.commit()
            }
            R.id.login_reg->{
                startActivity(Intent(this@LoginActivity, LoginActivity::class.java))
            }
            R.id.wei_login->{
                val req = SendAuth.Req()
                req.scope = "snsapi_userinfo"
                req.state = "diandi_wx_login"
                wxapi!!.sendReq(req)
            }

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        if(loginPresenter!=null)
        {
            loginPresenter!!.detachView()
        }
    }
}
