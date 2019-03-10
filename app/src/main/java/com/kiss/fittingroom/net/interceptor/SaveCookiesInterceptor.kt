package com.lxy.wanandroidkt.common.net.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import android.text.TextUtils
import android.content.Context
import com.kiss.fittingroom.net.URLManager
import java.util.HashSet


/**
 *
 * @ProjectName:    wanandroidkt
 * @Package:        com.lxy.wanandroidkt.common.net.interceptor
 * @ClassName:      SaveCookiesInterceptor
 * @Description:    Cookies保存处理处理
 * @Author:         lxy
 * @CreateDate:     2019/2/12 10:53
 * @UpdateUser:     lxy
 * @UpdateDate:     2019/2/12 10:53
 * @UpdateRemark:
 * @Version:        1.0
 */
class SaveCookiesInterceptor  constructor(private val mContext: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

/*        //set-cookie可能为多个
        if (!response.headers("Set-Cookie").isEmpty() && request.url().toString() == (URLManager.BASE_URL+ URLManager.login)) { //只存储登录的Cookie
            val cookies = response.headers("Set-Cookie")
            val cookie = encodeCookie(cookies)
            saveCookie(request.url().toString(), request.url().host(), cookie)
        }*/

        return response
    }

    /**
     * 整合cookie为唯一字符串
     */
    private fun encodeCookie(cookies: List<String>): String {
        val sb = StringBuilder()
        val set = HashSet<String>()
        for (cookie in cookies) {
            val arr = cookie.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (s in arr) {
                if (set.contains(s)) {
                    continue
                }
                set.add(s)

            }
        }

        for (cookie in set) {
            sb.append(cookie).append(";")
        }

        val last = sb.lastIndexOf(";")
        if (sb.length - 1 == last) {
            sb.deleteCharAt(last)
        }

        return sb.toString()
    }

    /**
     * 保存cookie到本地，这里我们分别为该url和host设置相同的cookie，其中host可选
     * 这样能使得该cookie的应用范围更广
     */
    private fun saveCookie(url: String, domain: String, cookies: String) {
        val sp = mContext.getSharedPreferences(COOKIE_PREF, Context.MODE_PRIVATE)
        val editor = sp.edit()

        if (TextUtils.isEmpty(url)) {
            throw NullPointerException("url is null.")
        } else {
            editor.putString(url, cookies)
        }

        if (!TextUtils.isEmpty(domain)) {
            editor.putString(domain, cookies)
        }
        editor.apply()
    }

    companion object {

        const val COOKIE_PREF = "cookies_prefs"

        /**
         * 清除本地Cookie
         *
         * @param context Context
         */
        fun clearCookie(context: Context) {
            val sp = context.getSharedPreferences(COOKIE_PREF, Context.MODE_PRIVATE)
            sp.edit().clear().apply()
        }


        /**
         * 检查本地是否有Cookie
         */
        fun checklocalCookie(context: Context):Boolean{
            val sp = context.getSharedPreferences(COOKIE_PREF,Context.MODE_PRIVATE)
            return !sp.all.isEmpty()
        }
    }
}