package com.kiss.fittingroom.utils

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *
 * @ProjectName:    wanandroidkt
 * @Package:        com.lxy.wanandroidkt.common
 * @ClassName:      RxHandler
 * @Description:     Rxjava相关方法
 * @Author:         lxy
 * @CreateDate:     2019/1/22 17:46
 * @UpdateUser:     lxy
 * @UpdateDate:     2019/1/22 17:46
 * @UpdateRemark:
 * @Version:        1.0
 */
fun <T> Observable<T>  .ioToMain(): Observable<T> {
    return subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}


