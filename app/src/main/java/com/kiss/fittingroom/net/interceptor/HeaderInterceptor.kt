package com.kiss.fittingroom.net.interceptor

import com.kiss.fittingroom.comment.Preference
import okhttp3.Interceptor
import okhttp3.Response

/**
 *
 * @ProjectName:    wanandroidkt
 * @Package:        com.lxy.wanandroidkt.common.net.interceptor
 * @ClassName:      HeaderInterceptor
 * @Description:     添加固定头部参数
 * @Author:         lxy
 * @CreateDate:     2019/1/29 17:59
 * @UpdateUser:     lxy
 * @UpdateDate:     2019/1/29 17:59
 * @UpdateRemark:
 * @Version:        1.0
 */
class HeaderInterceptor :Interceptor{
    var token: String by Preference("token", "")


    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
                // Provide your custom header here
                .header("token", token)
                .method(originalRequest.method(), originalRequest.body())
        val request = requestBuilder.build()
       return chain.proceed(request)
    }
}