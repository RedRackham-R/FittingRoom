package com.kiss.fittingroom.weight.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.TextView
import com.kiss.fittingroom.R

/**
 *
 * @ProjectName:    wanandroidkt
 * @Package:        com.lxy.wanandroidkt.weight.dialog
 * @ClassName:      LoadingDialog
 * @Description:    Loading 弹出框
 * @Author:         lxy
 * @CreateDate:     2019/2/13 11:19
 * @UpdateUser:     lxy
 * @UpdateDate:     2019/2/13 11:19
 * @UpdateRemark:
 * @Version:        1.0
 */
class LoadingDialog(context: Context) : Dialog(context, R.style.style_loadingDialog) {
    private lateinit var messageTv :TextView

    private  var mMessage :String = DEFUALT_MESSAGE
    companion object {
        const val DEFUALT_MESSAGE = "加载中..."
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_loading)
        messageTv = findViewById(R.id.dialog_loading_message)
        messageTv.text = mMessage
    }


    fun setMessage(message:String){
        mMessage= message
    }

}