package com.kiss.fittingroom.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.widget.Toast
import com.kiss.fittingroom.R


/**
 *
 * @ProjectName:    WeixiaoScannerPay
 * @Package:        com.ocom.weixiaoscannerpay.utils
 * @ClassName:      ToastUtils
 * @Description:     Toast工具类
 * @Author:         lxy
 * @CreateDate:     2018/12/24 15:15
 * @UpdateUser:     lxy
 * @UpdateDate:     2018/12/24 15:15
 * @UpdateRemark:
 * @Version:        1.0
 */
object ToastUtil {

    private var toast: Toast? = null//实现不管我们触发多少次Toast调用，都只会持续一次Toast显示的时长

    @SuppressLint("ShowToast")
            /**
     * 短时间显示Toast【居下】
     * @param msg 显示的内容-字符串
     */
    fun showShortToast(context:Context,msg: String) {
            if (toast == null) {
                toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
            } else {
                toast!!.setText(msg)
            }
            //1、setGravity方法必须放到这里，否则会出现toast始终按照第一次显示的位置进行显示（比如第一次是在底部显示，那么即使设置setGravity在中间，也不管用）
            //2、虽然默认是在底部显示，但是，因为这个工具类实现了中间显示，所以需要还原，还原方式如下：
            toast!!.setGravity(Gravity.BOTTOM, 0, dip2px(context, 64f))
            toast!!.show()

    }

    @SuppressLint("ShowToast")
            /**
     * 短时间显示Toast【居中】
     * @param msg 显示的内容-字符串
     */
    fun showShortToastCenter(context:Context,msg: String) {
            if (toast == null) {
                toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
            } else {
                toast!!.setText(msg)
            }
            toast!!.setGravity(Gravity.CENTER, 0, 0)
            toast!!.show()

    }

    /**
     * 短时间显示Toast【居上】
     * @param msg 显示的内容-字符串
     */
    fun showShortToastTop(context:Context,msg: String) {
            if (toast == null) {
                toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
            } else {
                toast!!.setText(msg)
            }
            toast!!.setGravity(Gravity.TOP, 0, 0)
            toast!!.show()

    }

    @SuppressLint("ShowToast")
            /**
     * 长时间显示Toast【居下】
     * @param msg 显示的内容-字符串
     */
    fun showLongToast(context:Context,msg: String) {
            if (toast == null) {
                toast = Toast.makeText(context, msg, Toast.LENGTH_LONG)
            } else {
                toast!!.setText(msg)
            }
            toast!!.setGravity(Gravity.BOTTOM, 0, dip2px(context, 64f))
            toast!!.show()

    }

    /**
     * 长时间显示Toast【居中】
     * @param msg 显示的内容-字符串
     */
    fun showLongToastCenter(context:Context,msg: String) {
            if (toast == null) {
                toast = Toast.makeText(context, msg, Toast.LENGTH_LONG)
            } else {
                toast!!.setText(msg)
            }
            toast!!.setGravity(Gravity.CENTER, 0, 0)
            toast!!.show()

    }

    @SuppressLint("ShowToast")
            /**
     * 长时间显示Toast【居上】
     * @param msg 显示的内容-字符串
     */
    fun showLongToastTop(context:Context,msg: String) {
            if (toast == null) {
                toast = Toast.makeText(context, msg, Toast.LENGTH_LONG)
            } else {
                toast!!.setText(msg)
            }
            toast!!.setGravity(Gravity.TOP, 0, 0)
            toast!!.show()

    }


    /**
     * 检查当前是否联网 如果当前无网络则显示tost
     */
    fun checkShowNetConnectToast(context: Context){
        if (!NetworkUtil.isConnected(context)){
            showShortToast(context,context.getString(R.string.net_disconnect))
        }
    }
    /**
     * 检查当前是否联网 如果当前无网络则显示tost 有网络则显示目标信息
     */
    fun checkShowNetConnectToast(context: Context,otherMessage:String?){
        if (!NetworkUtil.isConnected(context)){
            showShortToast(context,context.getString(R.string.net_disconnect))
        }else{
            if (otherMessage == null){
                showShortToast(context,context.getString(R.string.error_unknow))
            }else{
                showShortToast(context,otherMessage)
            }

        }
    }





    /*=================================常用公共方法============================*/
    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.getResources().getDisplayMetrics().density
        return (dpValue * scale + 0.5f).toInt()
    }
}