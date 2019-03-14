package com.kiss.fittingroom.entity

/**
 * 店铺列表
 */
data class TestMarketListEntity (
    var histroy:String ,//历史 1小时前 激烈**海胆下的订单卖家发货了
    var shopName:String,//店铺名称
    var shopImage:Int,//店铺头像
    var onLine:String,//在线状态
    var goodsImage_1:Int,//第一件商品的图片
    var goodsPrice_1:String,//第一件商品的价格
    var goodsImage_2:Int,//第二件商品的图片
    var goodsPrice_2:String,//第二件商品的价格
    var goodsImage_3:Int,//第三件商品的图片
    var goodsPrice_3:String//第三件商品的价格

)