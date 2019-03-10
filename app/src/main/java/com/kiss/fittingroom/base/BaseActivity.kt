package com.kiss.fittingroom.base

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import com.kiss.fittingroom.R
import org.greenrobot.eventbus.EventBus

abstract class BaseActivity : AppCompatActivity() {
    //提示view
    protected lateinit var mTipsView: View
    protected lateinit var mTipsViewText:TextView
    protected lateinit var mWindowManager: WindowManager
    protected lateinit var mLayoutParams: WindowManager.LayoutParams

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        super.onCreate(savedInstanceState)
        setContentView(setAttachLayoutRes())
        if (isUseEventBus()) {
            EventBus.getDefault().register(this)
        }
        initTipsView()

        onActivityCreat(savedInstanceState)
    }


    fun start(targetCls: Class<*>) {
        startActivity(Intent(this, targetCls))
    }


    fun startWithOption(targetCls: Class<*> ,option: ActivityOptions){
        startActivity(Intent(this, targetCls),option.toBundle())
    }


    fun startWithFinish(targetCls: Class<*>) {
        startActivity(Intent(this, targetCls))
        finish()
    }








    fun startOptionWithFinish(targetCls: Class<*> ,option: ActivityOptions) {
        startActivity(Intent(this, targetCls),option.toBundle())
        finish()
    }

    //-----------------------------公开方法--------------------------
    /**
     * fragment切换
     */
    fun showHideFragment(showFragment: Fragment, hideFragment: BaseFragment) {
        supportFragmentManager.beginTransaction().show(showFragment).hide(hideFragment).commit()
    }

    /**
     * 显示tip
     */
    fun showTips(tips:String,positionY:Int){
        if (mTipsView.parent == null) {
            mTipsViewText.text = tips
            mLayoutParams.y = positionY
            mWindowManager.addView(mTipsView, mLayoutParams)
        }else{
            hideTips()
            mTipsViewText.text = tips
            mLayoutParams.y = positionY
            mWindowManager.addView(mTipsView, mLayoutParams)
        }
    }

    /**
     * 隐藏tip
     */
    fun hideTips(){
        if (mTipsView.parent == null) {
            mWindowManager.removeView(mTipsView)
        }
    }


    //--------------------------内部方法-----------------

    /**
     * 设置tipsView 用于提示
     */
    private fun initTipsView() {
        mTipsView = layoutInflater.inflate(R.layout.layout_tips_view, null)
        mTipsViewText = mTipsView.findViewById(R.id.layout_tips_view_text)
        mWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mLayoutParams = WindowManager.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT)
        mLayoutParams.gravity = Gravity.TOP
        mLayoutParams.x = 0
        mLayoutParams.y = 0
        mLayoutParams.windowAnimations = R.style.style_anim_tipsView // add animations
    }


    //--------------------------生命周期--------------------


    override fun onDestroy() {
        super.onDestroy()
        if (isUseEventBus()) {
            EventBus.getDefault().unregister(this)
        }
        if (mTipsView.parent != null){
            mWindowManager.removeView(mTipsView)
        }
    }


    //------------------------重写方法------------------------------


    /**
     * activity创建
     */
    abstract fun onActivityCreat(savedInstanceState: Bundle?)
    /**
     * 设置布局文件
     */
    abstract fun setAttachLayoutRes(): Int

    /**
     * 是否使用eventBus
     */
    open fun isUseEventBus(): Boolean = false

}
