package com.kiss.fittingroom.view.market

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kiss.fittingroom.R
import com.kiss.fittingroom.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_market.*
import kotlinx.android.synthetic.main.layout_list_refresh.*

/**
 * 商城模块
 */
class MarketFragment :BaseFragment() {

    private lateinit var mEmptyView: View
    private lateinit var mErrorView: View
    private lateinit var mLoadingView: View

    companion object {
        fun newInstance(): MarketFragment {
            return MarketFragment()
        }
    }

    override fun onBindView(rootView: View, savedInstanceState: Bundle?) {
        initViews()
    }

    override fun setLayout(): Any {
        return R.layout.fragment_market
    }

    private fun initViews(){
        mEmptyView = LayoutInflater.from(context).inflate(R.layout.layout_load_empty, layout_list_refresh_recycView.parent as ViewGroup, false)
        mLoadingView = LayoutInflater.from(context).inflate(R.layout.layout_loading, layout_list_refresh_recycView.parent as ViewGroup, false)
        mErrorView = LayoutInflater.from(context).inflate(R.layout.layout_load_error, layout_list_refresh_recycView.parent as ViewGroup, false)

        layout_list_refresh_recycView.run {

        }
        layout_list_refresh_layout.run {

        }



    }
}