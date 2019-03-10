package com.kiss.fittingroom.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.telephony.TelephonyManager
import java.io.IOException
import java.net.HttpURLConnection
import java.net.NetworkInterface
import java.net.SocketException
import java.net.URL


/**
 * 网络工具类
 */
object NetworkUtil {
    var NET_CNNT_BAIDU_OK = 1 // NetworkAvailable
    var NET_CNNT_BAIDU_TIMEOUT = 2 // no NetworkAvailable
    var NET_NOT_PREPARE = 3 // Net no ready
    var NET_ERROR = 4 //net error
    private val TIMEOUT = 3000 // TIMEOUT
    /**
     * check NetworkAvailable
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun isNetworkAvailable(context: Context): Boolean {
        val manager = context.applicationContext.getSystemService(
                Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = manager.activeNetworkInfo
        return !(null == info || !info.isAvailable)
    }

    /**
     * 得到ip地址
     *
     * @return
     */
    @JvmStatic
    fun getLocalIpAddress(): String {
        var ret = ""
        try {
            val en = NetworkInterface.getNetworkInterfaces()
            while (en.hasMoreElements()) {
                val enumIpAddress = en.nextElement().inetAddresses
                while (enumIpAddress.hasMoreElements()) {
                    val netAddress = enumIpAddress.nextElement()
                    if (!netAddress.isLoopbackAddress) {
                        ret = netAddress.hostAddress.toString()
                    }
                }
            }
        } catch (ex: SocketException) {
            ex.printStackTrace()
        }

        return ret
    }


    /**
     * ping "http://www.baidu.com"
     *
     * @return
     */
    @JvmStatic
    private fun pingNetWork(): Boolean {
        var result = false
        var httpUrl: HttpURLConnection? = null
        try {
            httpUrl = URL("http://www.baidu.com")
                    .openConnection() as HttpURLConnection
            httpUrl.connectTimeout = TIMEOUT
            httpUrl.connect()
            result = true
        } catch (e: IOException) {
        } finally {
            if (null != httpUrl) {
                httpUrl.disconnect()
            }
        }
        return result
    }

    /**
     * check is3G
     *
     * @param context
     * @return boolean
     */
    @JvmStatic
    fun is3G(context: Context): Boolean {
        val connectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetInfo = connectivityManager.activeNetworkInfo
        return activeNetInfo != null && activeNetInfo.type == ConnectivityManager.TYPE_MOBILE
    }



    /**
     * is2G
     *
     * @param context
     * @return boolean
     */
    @JvmStatic
    fun is2G(context: Context): Boolean {
        val connectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetInfo = connectivityManager.activeNetworkInfo
        return activeNetInfo != null && (activeNetInfo.subtype == TelephonyManager.NETWORK_TYPE_EDGE
                || activeNetInfo.subtype == TelephonyManager.NETWORK_TYPE_GPRS || activeNetInfo
                .subtype == TelephonyManager.NETWORK_TYPE_CDMA)
    }

    /**
     * is wifi on
     */
    @JvmStatic
    fun isWifiEnabled(context: Context): Boolean {
        val mgrConn = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val mgrTel = context
                .getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return mgrConn.activeNetworkInfo != null && mgrConn
                .activeNetworkInfo.state == NetworkInfo.State.CONNECTED || mgrTel
                .networkType == TelephonyManager.NETWORK_TYPE_UMTS
    }

    /**
     * 检测当前打开的网络类型是否WIFI
     *
     * @param context 上下文
     * @return 是否是Wifi上网
     */
    fun isWifi(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context
                .CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetInfo = connectivityManager.activeNetworkInfo
        return activeNetInfo != null && activeNetInfo.type == ConnectivityManager.TYPE_WIFI
    }

    /**
     * 是否有网
     */
    fun isConnected(context: Context): Boolean {
        return netSatus(context) != NetworkUtil.NetState.NET_NO
    }

    /**
     * 枚举网络状态 NET_NO：没有网络 NET_2G:2g网络 NET_3G：3g网络 NET_4G：4g网络 NET_WIFI：wifi
     * NET_UNKNOWN：未知网络
     */
    enum class NetState {
        NET_NO, NET_2G, NET_3G, NET_4G, NET_WIFI, NET_UNKNOWN
    }

    /**
     * 判断当前是否网络连接
     *
     * @param context 上下文
     * @return 状态码
     */
    fun netSatus(context: Context): NetState {
        var stateCode = NetworkUtil.NetState.NET_NO
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val ni = cm.activeNetworkInfo
        if (ni != null && ni.isConnectedOrConnecting) {
            stateCode = when (ni.type) {
                ConnectivityManager.TYPE_WIFI -> NetworkUtil.NetState.NET_WIFI
                ConnectivityManager.TYPE_MOBILE -> when (ni.subtype) {
                    TelephonyManager.NETWORK_TYPE_GPRS // 联通2g
                        , TelephonyManager.NETWORK_TYPE_CDMA // 电信2g
                        , TelephonyManager.NETWORK_TYPE_EDGE // 移动2g
                        , TelephonyManager.NETWORK_TYPE_1xRTT, TelephonyManager.NETWORK_TYPE_IDEN -> NetworkUtil.NetState.NET_2G
                    TelephonyManager.NETWORK_TYPE_EVDO_A // 电信3g
                        , TelephonyManager.NETWORK_TYPE_UMTS, TelephonyManager.NETWORK_TYPE_EVDO_0, TelephonyManager.NETWORK_TYPE_HSDPA, TelephonyManager.NETWORK_TYPE_HSUPA, TelephonyManager.NETWORK_TYPE_HSPA, TelephonyManager.NETWORK_TYPE_EVDO_B, TelephonyManager.NETWORK_TYPE_EHRPD, TelephonyManager.NETWORK_TYPE_HSPAP -> NetworkUtil.NetState.NET_3G
                    TelephonyManager.NETWORK_TYPE_LTE -> NetworkUtil.NetState.NET_4G
                    else -> NetworkUtil.NetState.NET_UNKNOWN
                }
                else -> NetworkUtil.NetState.NET_UNKNOWN
            }

        }
        return stateCode
    }


    /**
     * 无网络
     */
    val NETWORK_NONE = -1

    /**
     * 移动网络
     */
    val NETWORK_MOBILE = 0

    /**
     * WIFI
     */
    val NETWORK_WIFI = 1

    /**
     * 网线连接
     */
    val NETWORK_ETHERNET = 2

    /**
     * 获取当前网络状态
     * @param context
     * @return
     */
    fun getNetworkState(context: Context): Int {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = connectivityManager.activeNetworkInfo
        if (info != null && info.isConnected) {
            when {
                info.type == ConnectivityManager.TYPE_MOBILE -> return NETWORK_MOBILE
                info.type == ConnectivityManager.TYPE_WIFI -> return NETWORK_WIFI
                info.type == ConnectivityManager.TYPE_ETHERNET -> return NETWORK_ETHERNET
            }
        } else {
            return NETWORK_NONE
        }
        return NETWORK_NONE
    }

}