package com.wd.tech.wxapi

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.wd.tech.mvp.model.bean.LoginBean
import com.wd.tech.mvp.model.utils.WeiXinUtil
import com.wd.tech.mvp.presenter.presenterimpl.WechatLoginPresenter
import com.wd.tech.mvp.view.activity.MainActivity
import com.wd.tech.mvp.view.base.BaseActivity
import com.wd.tech.mvp.view.contract.Contract


class WXEntryActivity : IWXAPIEventHandler, BaseActivity<Contract.IWechatLoginView, WechatLoginPresenter>(),Contract.IWechatLoginView {
    var code: String? = null
    var wechatLoginPresenter:WechatLoginPresenter?=null
    override fun initActivityView(savedInstanceState: Bundle?) {

    }
    override fun createPresenter(): WechatLoginPresenter? {
        wechatLoginPresenter = WechatLoginPresenter(this)
        return wechatLoginPresenter
    }
    override fun initData() {

    }

    override fun initView() {
        WeiXinUtil.reg(this@WXEntryActivity)!!.handleIntent(intent, this)

    }

    override fun onIWechatLoginSuccess(any: Any) {
        if(any is LoginBean)
        {
            Toast.makeText(this, any.message, Toast.LENGTH_SHORT).show()
            val sp = getSharedPreferences("User", Context.MODE_PRIVATE)
            sp.edit().putString("userId", any.result.userId).putString("sessionId", any.result.sessionId).putInt("vip",any.result.whetherVip).commit()
            finish()
        }else{
            Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show()
        }

    }
    override fun onIWechatLoginFail() {

    }
    override fun onResp(p0: BaseResp?) {
        when (p0!!.errCode) {
            BaseResp.ErrCode.ERR_OK -> {
                code = (p0 as SendAuth.Resp).code
                var ak:String = "0110010010000"
                wechatLoginPresenter?.onIWechatLoginPre(ak,code!!)
            }
            ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX -> {
                //分享回调,处理分享成功后的逻辑
                Toast.makeText(this@WXEntryActivity,"分享成功",Toast.LENGTH_LONG).show()
                Toast.makeText(this@WXEntryActivity,"分享失败",Toast.LENGTH_LONG).show()
                finish()
            }
            else -> {
            }
        }
    }

    override fun onReq(p0: BaseReq?) {

    }
}