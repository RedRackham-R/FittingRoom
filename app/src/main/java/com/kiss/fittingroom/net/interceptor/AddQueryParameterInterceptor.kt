package com.kiss.fittingroom.net.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 *
 * @ProjectName:    wanandroidkt
 * @Package:        com.lxy.wanandroidkt.common.net.interceptor
 * @ClassName:      AddQueryParameterInterceptor
 * @Description:     添加公共参数
 * @Author:         lxy
 * @CreateDate:     2019/2/12 11:04
 * @UpdateUser:     lxy
 * @UpdateDate:     2019/2/12 11:04
 * @UpdateRemark:
 * @Version:        1.0
 */
class AddQueryParameterInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val request: Request
        val modifiedUrl = originalRequest.url().newBuilder()
                // Provide your custom parameter here
/*                .addQueryParameter("phoneSystem", "")
                .addQueryParameter("phoneModel", "")*/
                .build()
        request = originalRequest.newBuilder().url(modifiedUrl).build()
        return chain.proceed(request)
    }

}