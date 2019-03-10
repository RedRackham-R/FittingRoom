package com.kiss.fittingroom.base

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kiss.fittingroom.R
import org.greenrobot.eventbus.EventBus

abstract class BaseFragment : Fragment() {

    private var mRootView: View? = null

    private var isViewPrepare = false//视图是否加载完成

    private var hasLoadData = false//是否加载过数据



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: View
        val layoutObject: Any = setLayout()
        rootView = when (layoutObject) {
            is Int -> {
                val viewId: Int = layoutObject
                inflater.inflate(viewId, container, false)
            }
            is View -> layoutObject
            else -> throw ClassCastException("setLayout() 传入参数类型有误")
        }
        mRootView = rootView

        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isUseEventBus()) {
            EventBus.getDefault().register(this)
        }
        isViewPrepare = true
        onBindView(mRootView!!, savedInstanceState)
        lazyLoadDataIfPrepared()
    }


    override fun onDestroy() {
        super.onDestroy()
        if (isUseEventBus()) {
            EventBus.getDefault().unregister(this)
        }

    }

    /**
     * fragment是否可见
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            lazyLoadDataIfPrepared()
        }
    }


    private fun lazyLoadDataIfPrepared() {
        if (userVisibleHint && isViewPrepare && !hasLoadData) {
            lazyLoad()
            hasLoadData = true
        }
    }


    //-----------------------------------------------------------------实现方法
    abstract fun onBindView(rootView: View, @Nullable savedInstanceState: Bundle?)

    abstract fun setLayout(): Any

    //-----------------------------------------------------------------对外公开
    open fun <T : View> bind(@IdRes id: Int): T {
        return mRootView!!.findViewById(id)
    }

    /**
     * 懒加载 或者重新加载
     */
    open fun lazyLoad() {}


    /**
     * 注册eventBus
     */
    open fun isUseEventBus(): Boolean = false



}