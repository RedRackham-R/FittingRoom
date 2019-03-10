package com.kiss.fittingroom.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.kiss.fittingroom.R
import com.kiss.fittingroom.base.BaseActivity
import com.kiss.fittingroom.base.BaseFragment
import com.kiss.fittingroom.utils.StatusBarCompat
import com.kiss.fittingroom.view.live.LiveFragmnet
import com.kiss.fittingroom.view.market.MarketFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private var currentIndex = 0//标记位
    private var itemFragments = arrayOfNulls<BaseFragment>(2)//页面容器


    override fun onActivityCreat(savedInstanceState: Bundle?) {
        StatusBarCompat.translucentStatusBar(this@MainActivity, false)
        initViews()
        initFragments()
    }

    override fun setAttachLayoutRes(): Int {
        return R.layout.activity_main
    }

    private fun initViews() {
        activity_main_bottomNavigationView.run {//底部导航栏
            setOnNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_bottom_navigation_market -> {//商城
                        if (currentIndex != 0) {
                            showHideFragment(itemFragments[0]!!, itemFragments[currentIndex]!!)
                            currentIndex = 0
                        }

                        true
                    }
                    R.id.menu_bottom_navigation_live -> {//直播
                        if (currentIndex != 1) {
                            if (itemFragments[1] == null) {
                                itemFragments[1] = LiveFragmnet.newInstance()
                                supportFragmentManager.beginTransaction().add(R.id.activity_main_container, itemFragments[1]!!).commit()
                            }
                            showHideFragment(itemFragments[1]!!, itemFragments[currentIndex]!!)
                            currentIndex = 1
                        }
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
        }

    }

    /**
     * 设置各个界面模块
     */
    private fun initFragments() {
        itemFragments[0] = MarketFragment.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.activity_main_container, itemFragments[currentIndex]!!)
            .commit()
        supportFragmentManager.beginTransaction().show(itemFragments[currentIndex]!!).commit()
    }


}
