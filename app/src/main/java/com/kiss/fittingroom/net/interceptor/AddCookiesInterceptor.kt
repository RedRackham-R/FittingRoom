package com.kiss.fittingroom.net.interceptor

import android.text.TextUtils
import android.content.Context
import com.lxy.wanandroidkt.common.net.interceptor.SaveCookiesInterceptor
import okhttp3.Interceptor
import okhttp3.Response


/**
 *
 * @ProjectName:    wanandroidkt
 * @Package:        com.lxy.wanandroidkt.common.net.interceptor
 * @ClassName:      AddCookiesInterceptor
 * @Description:     添加Cookies
 * @Author:         lxy
 * @CreateDate:     2019/2/12 11:00
 * @UpdateUser:     lxy
 * @UpdateDate:     2019/2/12 11:00
 * @UpdateRemark:
 * @Version:        1.0
 */
class AddCookiesInterceptor(private val mContext: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        val cookie = getCookie(request.url().toString(), request.url().host())
        if (!TextUtils.isEmpty(cookie)) {
            builder.addHeader("Cookie", cookie)
        }
        return chain.proceed(builder.build())
    }


    private fun getCookie(url: String, domain: String): String? {
        val sp = mContext.getSharedPreferences(SaveCookiesInterceptor.COOKIE_PREF, Context.MODE_PRIVATE)
        if (!TextUtils.isEmpty(url) && sp.contains(url) && !TextUtils.isEmpty(sp.getString(url, ""))) {
            return sp.getString(url, "")
        }
        return if (!TextUtils.isEmpty(domain) && sp.contains(domain) && !TextUtils.isEmpty(sp.getString(domain, ""))) {
            sp.getString(domain, "")
        } else null

    }


}