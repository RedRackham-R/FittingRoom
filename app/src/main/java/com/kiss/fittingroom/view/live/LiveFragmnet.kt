package com.kiss.fittingroom.view.live

import android.os.Bundle
import android.view.View
import com.kiss.fittingroom.R
import com.kiss.fittingroom.base.BaseFragment
import com.kiss.fittingroom.view.market.MarketFragment

class LiveFragmnet :BaseFragment() {

    companion object {
        fun newInstance(): LiveFragmnet {
            return LiveFragmnet()
        }
    }
    override fun onBindView(rootView: View, savedInstanceState: Bundle?) {

    }

    override fun setLayout(): Any {
        return R.layout.fragment_live
    }
}