package com.kiss.fittingroom.net

import com.kiss.fittingroom.app.FittingRoomApplication
import com.kiss.fittingroom.net.interceptor.AddCookiesInterceptor
import com.kiss.fittingroom.net.interceptor.CacheInterceptor
import com.kiss.fittingroom.net.interceptor.HeaderInterceptor
import com.lxy.wanandroidkt.common.net.interceptor.SaveCookiesInterceptor
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 *
 * @ProjectName:    wanandroidkt
 * @Package:        com.lxy.wanandroidkt.common.net
 * @ClassName:      NetworkManager
 * @Description:     网络请求管理类 采用 retrofit + RxJava
 * @Author:         lxy
 * @CreateDate:     2019/1/7 17:46
 * @UpdateUser:     lxy
 * @UpdateDate:     2019/1/7 17:46
 * @UpdateRemark:
 * @Version:        1.0
 */
object NetworkManager {

     fun getRetrofit(): Retrofit {
        return Retrofit
                .Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(URLManager.BASE_URL)
                .client(getOkHttpClient())
                .build()
    }

    /**
     * 设置okhttp各项参数
     */
    private fun getOkHttpClient(): OkHttpClient {
        //添加一个log拦截器,打印所有的log
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        //可以设置请求过滤的水平,body,basic,headers
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        //设置 请求的缓存的大小跟位置
        val cacheFile = File(FittingRoomApplication.INSTANCE.cacheDir, "cache")
        val cache = Cache(cacheFile, 1024 * 1024 * 100) //100Mb 缓存的大小

        return OkHttpClient.Builder()
                //.addInterceptor(addQueryParameterInterceptor())  //设置公共参数
                .addInterceptor(CacheInterceptor())//设置缓存
                .addInterceptor(HeaderInterceptor()) // 添加固定head
                .addInterceptor(httpLoggingInterceptor) //日志,所有的请求响应度看到
                .addInterceptor(SaveCookiesInterceptor(FittingRoomApplication.INSTANCE))//本地持久化Cookie
                .addInterceptor(AddCookiesInterceptor(FittingRoomApplication.INSTANCE))//添加Cookie到请求
                .cache(cache)//设置缓存大小
                .connectTimeout(60L, TimeUnit.SECONDS)
                .readTimeout(60L, TimeUnit.SECONDS)
                .writeTimeout(60L, TimeUnit.SECONDS)
                .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER) //设置所有证书通过 不安全
                .build()
    }


}