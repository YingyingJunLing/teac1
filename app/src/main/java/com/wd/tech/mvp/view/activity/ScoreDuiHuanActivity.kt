package com.wd.tech.mvp.view.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.InfoPayByIntegralBean
import com.wd.tech.mvp.model.bean.UserInfoBean
import com.wd.tech.mvp.model.utils.AlterAndAnimationUtil
import com.wd.tech.mvp.presenter.presenterimpl.InfoPayByIntegralPresenter
import com.wd.tech.mvp.view.base.BaseActivity
import com.wd.tech.mvp.view.contract.Contract
import kotlinx.android.synthetic.main.activity_all_plate_item.*
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_score_dui_huan.*
import kotlinx.android.synthetic.main.detail_fail_dialog_item.*
import kotlinx.android.synthetic.main.detail_fail_dialog_item.view.*
import kotlinx.android.synthetic.main.detail_success_dialog_item.*
import kotlinx.android.synthetic.main.dialog_camera_layout.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.text.SimpleDateFormat
import java.util.*

class ScoreDuiHuanActivity : BaseActivity<Contract.IInfoPayByIntegralView,InfoPayByIntegralPresenter>() ,Contract.IInfoPayByIntegralView{
    var infoPayByIntegralPresenter:InfoPayByIntegralPresenter?=null
    var sid :Int = 0
    var needScore :Int = 0
   var  integrals:Int =0
    val FORMAT_DATE_TIME_PATTERN = "HH:mm:ss"
    var alertAndAnimationUtils: AlterAndAnimationUtil? = null
    var hashMap: HashMap<String, String> = HashMap()
    override fun initData() {
   infoPayByIntegralPresenter!!.onIInfoPayByIntegralPre(hashMap,sid,needScore)
    }

    override fun initView() {
        alertAndAnimationUtils =AlterAndAnimationUtil()
        var sharedPreferences: SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)
        var userId = sharedPreferences.getString("userId", "0")
        var sessionId = sharedPreferences.getString("sessionId", "0")
        hashMap.put("userId", userId)
        hashMap.put("sessionId", sessionId)
     var intent =   intent
        val id = intent.getIntExtra("id", 0)
        val whetherCollection = intent.getIntExtra("whetherCollection", 0)
        val score = intent.getIntExtra("score", 0)
        val source = intent.getStringExtra("source")
        val time= intent.getLongExtra("time",0)
        val content=  intent.getStringExtra("content")
        val share = intent.getStringExtra("share")
        val img = intent.getStringExtra("img")
        sid =id
        needScore  = score
        if (whetherCollection == 1) {
            info_zan.setImageResource(R.mipmap.common_icon_collect_s)
        } else {
            info_zan.setImageResource(R.mipmap.common_icon_collect)
        }
        score_info_title.text=title
        score_info_summary.text = content
        score_info_source.text =source
        score_share_num.text= share
        need_score.text="所需积分："+score
        my_score_dui.text ="我的积分："+integrals
        var dateFormat = SimpleDateFormat(FORMAT_DATE_TIME_PATTERN, Locale.getDefault())
        score_info_time.setText(dateFormat.format(time))
       score_info_img.setImageURI(img)
        //返回按钮
        duihuan_back.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                finish()
            }

        })
        lijiduihuan.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) = if(needScore>integrals)
            {
                val view = View.inflate(this@ScoreDuiHuanActivity, R.layout.detail_fail_dialog_item, null)
                //点击叉号
               view. delete_fail.setOnClickListener(object :View.OnClickListener{
                    override fun onClick(v: View?) {
                alertAndAnimationUtils!!.hideDialog()
                    }

                })
                //点击取消
                view.cancel_fail.setOnClickListener(object :View.OnClickListener{
                    override fun onClick(v: View?) {
                        alertAndAnimationUtils!!.hideDialog()
                    }
                })
                //点击去换积分
               view. makeTask_fail.setOnClickListener(object :View.OnClickListener{
                    override fun onClick(v: View?) {
                        val intent1 = Intent(this@ScoreDuiHuanActivity, MyTaskActivity::class.java)
                        startActivity(intent1)
                    }
                })
                alertAndAnimationUtils!!.AlterDialog(this@ScoreDuiHuanActivity, view)

            }else{
                val view = View.inflate(this@ScoreDuiHuanActivity, R.layout.detail_success_dialog_item, null)
                delete_success.setOnClickListener(object :View.OnClickListener{
                    override fun onClick(v: View?) {
                        alertAndAnimationUtils!!.hideDialog()
                    }

                })
                agagin_success.setOnClickListener(object :View.OnClickListener{
                    override fun onClick(v: View?) {
                        val intent12 = Intent(this@ScoreDuiHuanActivity, DetailsActivity::class.java)
                        startActivity(intent12)
                    }
                })
                alertAndAnimationUtils!!.AlterDialog(this@ScoreDuiHuanActivity, view)
            }
        })
    }

    override fun onSuccess(any: Any) {
        if(any is InfoPayByIntegralBean)
        {


        }

    }

    override fun onFail() {

    }

    override fun createPresenter(): InfoPayByIntegralPresenter? {
        infoPayByIntegralPresenter = InfoPayByIntegralPresenter(this)
        return infoPayByIntegralPresenter
    }

    override fun initActivityView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_score_dui_huan)
        EventBus.getDefault().register(this)
    }
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    fun getEventBus(userInfoBean: UserInfoBean )
    {
        val integral = userInfoBean.result.integral
        integrals = integral
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
        if(infoPayByIntegralPresenter!=null)
        {
            infoPayByIntegralPresenter!!.detachView()
        }
    }
}


