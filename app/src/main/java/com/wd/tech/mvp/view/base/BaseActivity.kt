package com.wd.tech.mvp.view.base

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import com.wd.tech.mvp.presenter.base.BasePresenter
import me.imid.swipebacklayout.lib.app.SwipeBackActivity
import me.jessyan.autosize.internal.CustomAdapt

abstract class BaseActivity<V,T : BasePresenter<V>> : SwipeBackActivity(), CustomAdapt {

    override fun isBaseOnWidth(): Boolean {
        return false
    }

    override fun getSizeInDp(): Float {
        return 667F
    }

    private var startInAnimationResources = 0
    private var startOutAnimationResources = 0
    private var finishInAnimationResources = 0
    private var finishOutAnimationResources = 0
    private var isInAnimated = false//是否是初次创建的resume

    private var slideFinishLayout: SlideFinishLayout? = null
    private var isCanBack = true
    var basePresenter:T?=null
    var mContext: Context?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //加载布局
        initActivityView(savedInstanceState)
        mContext = this@BaseActivity
        //创建层
        basePresenter = createPresenter()
        //加载控件
        initView()
        //加载数据
        initData()
        //绑定p
        if (null !=basePresenter)
        {
            basePresenter!!.attachView(this as V)
        }
        //setInOutAnimation(R.anim.left_in, R.anim.left_out, R.anim.right_in, R.anim.right_out)

        //if (isCanBack) initSlideFinish()
        //权限
        statePermission()
    }

    abstract fun createPresenter(): T?

    abstract fun initActivityView(savedInstanceState: Bundle?)

    abstract fun initData()

    abstract fun initView()
    /**
     * 初始化右滑退出控件
     */
//    private fun initSlideFinish() {
//        slideFinishLayout = LayoutInflater.from(this).inflate(
//           R.layout.custom_slidefinish_container, null
//        ) as SlideFinishLayout
//        slideFinishLayout!!.attachToActivity(this)
//    }

    /**
     * 右滑返回：添加忽略view，内部维护一个list，可在一个页面添加多个忽略view
     */
//    fun addIgnoredView(v: View) {
//        slideFinishLayout?.addIgnoredView(v)
//    }

    /**
     * 设置当前页面是否支持滑动退出，需要写在继承该类的子类onCreate中super.onCreate();的前面
     *
     * @param isCanBack 是否能右滑finish
     */
//    fun setSlideFinish(isCanBack: Boolean) {
//        this.isCanBack = isCanBack
//    }

    /**
     * 设置打开界面和关闭界面的动画效果
     *
     * @param startInAnimationResources
     * @param startOutAnimationResources
     * @param finishInAnimationResources
     * @param finishOutAnimationResources
     */
//    fun setInOutAnimation(
//        startInAnimationResources: Int, startOutAnimationResources: Int,
//        finishInAnimationResources: Int, finishOutAnimationResources: Int
//    ) {
//        this.startInAnimationResources = startInAnimationResources
//        this.startOutAnimationResources = startOutAnimationResources
//        this.finishInAnimationResources = finishInAnimationResources
//        this.finishOutAnimationResources = finishOutAnimationResources
//    }

//    override fun onResume() {
//        super.onResume()
//        if (startOutAnimationResources !== 0) {
//            if (!isInAnimated) {
//                overridePendingTransition(startInAnimationResources, startOutAnimationResources)
//                isInAnimated = true
//            }
//        }
//    }

//    override fun finish() {
//        super.finish()
//        if (finishInAnimationResources !== 0) {
//            overridePendingTransition(finishInAnimationResources, finishOutAnimationResources)
//        }
//    }

    private fun statePermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val mStatenetwork = arrayOf(
                //window权限
                Manifest.permission.SYSTEM_ALERT_WINDOW,
                //写的权限
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                //读的权限
                Manifest.permission.READ_EXTERNAL_STORAGE,
                //入网权限
                Manifest.permission.ACCESS_NETWORK_STATE,
                //WIFI权限
                Manifest.permission.ACCESS_WIFI_STATE,
                //读手机权限
                Manifest.permission.READ_PHONE_STATE,
                //网络权限
                Manifest.permission.INTERNET,
                //位置权限
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
                //相机
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_APN_SETTINGS,
                Manifest.permission.ACCESS_NETWORK_STATE
            )
            ActivityCompat.requestPermissions(this, mStatenetwork, 100)
        }
    }
}