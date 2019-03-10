package com.kiss.fittingroom.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import com.kiss.fittingroom.R
import com.kiss.fittingroom.weight.dialog.LoadingDialog


/**
 *
 * @ProjectName:    wanandroidkt
 * @Package:        com.lxy.wanandroidkt.utils
 * @ClassName:      DialogUtils
 * @Description:    dialog辅助类
 * @Author:         lxy
 * @CreateDate:     2019/2/12 14:25
 * @UpdateUser:     lxy
 * @UpdateDate:     2019/2/12 14:25
 * @UpdateRemark:
 * @Version:        1.0
 */
object DialogUtils {

    @SuppressLint("InflateParams")
    fun creatLoadingDialog(context: Context,message:String,cancelable:Boolean): LoadingDialog {
        val loadingDialog = LoadingDialog(context)
        if (!message.isEmpty()){
            loadingDialog.setMessage(message)
        }

        loadingDialog.setCancelable(cancelable)
        return loadingDialog
    }


    /**
     * 创建一个含有确定和取消的dialog
     */
    fun creatSureCancelDialog(context: Context, title:String, message: String, suerListener: DialogInterface.OnClickListener, cancelListener:DialogInterface.OnClickListener):AlertDialog{
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setMessage(message)
        dialogBuilder.setTitle(title)
        dialogBuilder.setCancelable(true)
        dialogBuilder.setPositiveButton(context.getString(R.string.sure),suerListener)
        dialogBuilder.setNegativeButton(context.getString(R.string.cancel),cancelListener)
       return dialogBuilder.create()
    }



 /*   *//**
     * 生成一个加载的dialog
     *//*
    fun creatLoadingDialog(context: Context):LoadingDialog{
        val ld = LoadingDialog(context)
        ld.closeSuccessAnim()
                .closeFailedAnim()
        return ld
    }*/
}