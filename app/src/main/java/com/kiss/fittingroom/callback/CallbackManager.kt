package com.kiss.fittingroom.callback

import java.util.*


class CallbackManager private constructor() {
    companion object {
        private var callbackMap: HashMap<Any, ICallback<Any>> = HashMap()
        fun getInstance() = Holder.INSTACE
    }

    private object Holder {
        val INSTACE = CallbackManager()
    }

    fun <T: ICallback<Any>> addCallback(key: Any, callback: T) {
        callbackMap[key] = callback
    }

    fun getCallback(key: Any): ICallback<Any>?{
        return callbackMap[key]
    }



}