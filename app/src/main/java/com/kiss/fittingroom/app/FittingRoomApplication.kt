package com.kiss.fittingroom.app

import android.app.Application
import android.graphics.drawable.ColorDrawable
import android.support.v4.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.request.RequestOptions
import com.kiss.fittingroom.R
import com.scwang.smartrefresh.header.PhoenixHeader
import com.scwang.smartrefresh.header.StoreHouseHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlin.properties.Delegates

class FittingRoomApplication :Application() {
    companion object {
        var INSTANCE: Application by Delegates.notNull()
            private set
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        initGlide()
        initRefreshLayout()
    }

    private fun initGlide(){
        val requestOptions =  RequestOptions()
            .placeholder( ColorDrawable(ContextCompat.getColor(INSTANCE, R.color.app_img_background)))
            .error( ColorDrawable(ContextCompat.getColor(INSTANCE, R.color.app_img_background)))

        val glideBuilder = GlideBuilder()
        glideBuilder.setDefaultRequestOptions(requestOptions)
        Glide.init(INSTANCE,glideBuilder)
    }

    private fun initRefreshLayout() {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.app_theme, R.color.app_container_background)//全局设置主题颜色
            val storeHouseHeader = StoreHouseHeader(context)
            storeHouseHeader.initWithString("Fitting",32)
            val phoenixHeader = PhoenixHeader(context)
            phoenixHeader//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        }

    }
}