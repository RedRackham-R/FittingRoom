package com.kiss.fittingroom.entity

import android.widget.ImageView
import android.widget.TextView
import com.kiss.fittingroom.R

/**
 *
 * @ProjectName:    FittingRoom
 * @Package:        com.kiss.fittingroom.entity
 * @ClassName:      TestMarketBusinessEntity
 * @Description:    商圈
 * @Author:         lxy
 * @CreateDate:     2019/3/11 18:07
 * @UpdateUser:     lxy
 * @UpdateDate:     2019/3/11 18:07
 * @UpdateRemark:
 * @Version:        1.0
 */
data class TestMarketBusinessEntity (
    var img:Int,
    val title :String,
    val week:String,
    val wechat:String,
    val content:String,
    val onLine : String,
    val sale : String,
    val comment: String
)