package com.kiss.fittingroom.view.market

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import com.kiss.fittingroom.R
import com.kiss.fittingroom.base.BaseFragment
import com.kiss.fittingroom.weight.banner.MarketBannerImageLoader
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.layout_list_refresh.*
import android.widget.RelativeLayout
import com.kiss.fittingroom.adapter.*
import com.kiss.fittingroom.entity.*
import com.kiss.fittingroom.utils.StatusBarCompat
import com.kiss.fittingroom.utils.ToastUtil
import com.kiss.fittingroom.weight.gridView.AutoAdaptGridView
import kotlinx.android.synthetic.main.layout_market_bar.*


/**
 * 商城模块
 */
class MarketFragment : BaseFragment() {

    private lateinit var mEmptyView: View
    private lateinit var mErrorView: View
    private lateinit var mLoadingView: View

    private lateinit var mMarketListAdapter: MarketListAdapter
    private var recyclerViewScrollOffset: Int = 0

    private var mBannerHeight: Int = 0
    private var mToolbarHeight: Int = 0

    private lateinit var mBanner: Banner//banner
    private lateinit var mClassificationGridView: AutoAdaptGridView//分类部分
    private lateinit var mClassifitacionAdapter: MarketClassifitacionAdapter

    private lateinit var mLiveContainerView: View//直播部分
    private lateinit var mLiveGridView: AutoAdaptGridView
    private lateinit var mLiveAdapter: MarketLiveAdapter

    private lateinit var mHottestContainerView: View//热门部分
    private lateinit var mHottestGridView: AutoAdaptGridView
    private lateinit var mHottestGridAdapter: MarketHottestGridAdater

    private lateinit var mBusinessContainerView: View//商圈
    private lateinit var mBusinessGridView: AutoAdaptGridView
    private lateinit var mBussinessAdapter: MarketBusinessAdapter

    companion object {
        fun newInstance(): MarketFragment {
            return MarketFragment()
        }
    }

    override fun onBindView(rootView: View, savedInstanceState: Bundle?) {
        initViews()
        initData()
    }

    override fun setLayout(): Any {
        return R.layout.fragment_market
    }

    /**
     * 添加假数据
     */
    private fun initData() {
        //--------------RecyclerView数据
        val data: ArrayList<String> = ArrayList()
        for (i in 0..50) {
            data.add("测试数据$i")
        }
        mMarketListAdapter.setNewData(data)

        //--------------Banner数据
        val bannerTitles = ArrayList<String>()
        for (i in 0..2) {
            bannerTitles.add("测试banner $i")
        }
        val bannerData = ArrayList<Int>()

        for (i in 0..2) {
            when (i) {
                0 -> {
                    bannerData.add(R.drawable.test_img_1)
                }
                1 -> {
                    bannerData.add(R.drawable.test_img_2)
                }
                2 -> {
                    bannerData.add(R.drawable.test_img_3)
                }
            }
        }
        mBanner.setBannerTitles(bannerTitles)
        mBanner.update(bannerData)

        //--------------分类数据
        val classifitacinDataList: ArrayList<TestMarketClassificationEntity> = ArrayList()
        for (i in 0..9) {
            classifitacinDataList.add(TestMarketClassificationEntity("分类$i", R.drawable.ic_android_pink_600_24dp))
        }
        mClassifitacionAdapter.setNewData(classifitacinDataList)

        //--------------直播数据
        val liveDataList: ArrayList<TestMarketLiveEntity> = ArrayList()
        for (i in 0..26) {
            liveDataList.add(TestMarketLiveEntity(R.drawable.ic_android_pink_600_24dp, "主标题$i", "测试副标题$i"))
        }
        mLiveAdapter.setNewData(liveDataList)
        //--------------热门数据
        val hottestGridDataList: ArrayList<TestMarketHottestGirdEntity> = ArrayList()
        for (gridIndex in 0..1) {
            val hottestRecyclerDataList: ArrayList<TestMarketHottestRecyEntity> = ArrayList()
            for (recyIndex in 0..10) {
                hottestRecyclerDataList.add(
                    TestMarketHottestRecyEntity(
                        "热门数据$recyIndex",
                        R.drawable.ic_android_pink_600_24dp
                    )
                )
            }
            if (gridIndex == 0) {
                hottestGridDataList.add(
                    TestMarketHottestGirdEntity(
                        "早春新款",
                        hottestRecyclerDataList,
                        ContextCompat.getColor(this@MarketFragment.context!!, R.color.color_ogrange500)
                    )
                )
            } else {
                hottestGridDataList.add(
                    TestMarketHottestGirdEntity(
                        "初春必备$gridIndex",
                        hottestRecyclerDataList,
                        ContextCompat.getColor(this@MarketFragment.context!!, R.color.color_light_blue500)
                    )
                )

            }
        }
        mHottestGridAdapter.setNewData(hottestGridDataList)
        //--------------商圈数据
        val businessData: ArrayList<TestMarketBusinessEntity> = ArrayList()
        for(index in 0..2){
            businessData.add(TestMarketBusinessEntity(
                R.drawable.test_img_1,
                "测试标题$index",
                "4096",
                "6589",
                "测试说明测试说明测试说明测试说明测试说明测试说明测试说明测试说明测试说明测试说明测试说明测试说明" +
                        "测试说明测试说明测试说明测试说明测试说明测试说明测试说明测试说明测试说明测试说明测试说明测试说明测",
            "40.72万人在线",
                "出货53.58万款",
                "4689评论"))
        }
        mBussinessAdapter.setNewData(businessData)
    }


    private fun initViews() {
        mEmptyView = LayoutInflater.from(context)
            .inflate(R.layout.layout_load_empty, layout_list_refresh_recycView.parent as ViewGroup, false)
        mLoadingView = LayoutInflater.from(context)
            .inflate(R.layout.layout_loading, layout_list_refresh_recycView.parent as ViewGroup, false)
        mErrorView = LayoutInflater.from(context)
            .inflate(R.layout.layout_load_error, layout_list_refresh_recycView.parent as ViewGroup, false)



        //------------------------------------------------------toolbar
        layout_market_bar.run {
            //设置toolbar的margin 设置到状态栏下
            setBackgroundColor(ContextCompat.getColor(this@MarketFragment.context!!, R.color.color_brown500))
            //这个本来只是设置toolbar的margin
/*            val lp = layoutParams as LinearLayout.LayoutParams
            lp.setMargins(0, StatusBarCompat.getStatusBarHeight(this@MarketFragment.context!!), 0, 0)
            layoutParams = lp*/
            /*inflateMenu(com.kiss.fittingroom.R.menu.menu_main_search)*/
            viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                //获取view高度
                override fun onGlobalLayout() {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                    mToolbarHeight = layout_market_bar.measuredHeight
                    layout_market_bar.setBackgroundColor(
                        ContextCompat.getColor(
                            this@MarketFragment.context!!,
                            R.color.transparent
                        )
                    )
                }
            })
        }
        //------------------------------------------------------recyclerView
        mMarketListAdapter = MarketListAdapter()
        layout_list_refresh_recycView.run {
            layoutManager = LinearLayoutManager(this@MarketFragment.context)
            adapter = mMarketListAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    recyclerViewScrollOffset += dy
                    if (recyclerViewScrollOffset >= mBannerHeight - StatusBarCompat.getStatusBarHeight(this@MarketFragment.context!!) - mToolbarHeight) {
                        status_bar.setBackgroundColor(
                            ContextCompat.getColor(
                                this@MarketFragment.context!!,
                                R.color.app_theme
                            )
                        )
                        layout_market_bar.alpha = 1f
                        layout_market_bar.setBackgroundColor(
                            ContextCompat.getColor(
                                this@MarketFragment.context!!,
                                R.color.app_theme
                            )
                        )
                    } else {
                        status_bar.setBackgroundColor(
                            ContextCompat.getColor(
                                this@MarketFragment.context!!,
                                R.color.transparent
                            )
                        )
                        layout_market_bar.setBackgroundColor(
                            ContextCompat.getColor(
                                this@MarketFragment.context!!,
                                R.color.transparent
                            )
                        )
                    }
                }
            })
        }

        //------------------------------------------------------banner
        mBanner = LayoutInflater.from(context).inflate(
            R.layout.layout_market_banner,
            layout_list_refresh_recycView,
            false
        ) as Banner
        mBanner.run {
            viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                //获取View高度
                override fun onGlobalLayout() {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                    mBannerHeight = mBanner.measuredHeight
                }
            })

            setImageLoader(MarketBannerImageLoader())
            setOnBannerListener {

            }
            setBannerAnimation(Transformer.DepthPage)
            setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
        }
        mMarketListAdapter.addHeaderView(mBanner, 0)

        //------------------------------------------------------分类GridView
        mClassificationGridView = LayoutInflater.from(context).inflate(
            R.layout.layout_market_classification,
            layout_list_refresh_recycView,
            false
        ) as AutoAdaptGridView
        mClassifitacionAdapter = MarketClassifitacionAdapter(this@MarketFragment.context!!)
        mClassificationGridView.run {
            adapter = mClassifitacionAdapter
        }
        mMarketListAdapter.addHeaderView(mClassificationGridView, 1)
        //------------------------------------------------------直播GridView
        mLiveContainerView = LayoutInflater.from(context).inflate(
            R.layout.layout_market_live,
            layout_list_refresh_recycView,
            false
        ) as View
        mLiveGridView = mLiveContainerView.findViewById(R.id.layout_market_live_autoAdaptGridView)
        mLiveAdapter = MarketLiveAdapter(this@MarketFragment.context!!)
        mLiveGridView.run {
            adapter = mLiveAdapter
        }
        mMarketListAdapter.addHeaderView(mLiveContainerView, 2)
        //-------------------------------------------------------最热门
        mHottestContainerView = LayoutInflater.from(context).inflate(
            R.layout.layout_market_hottest,
            layout_list_refresh_recycView,
            false
        ) as View
        mHottestGridView = mHottestContainerView.findViewById(R.id.layout_market_hottest_autoAdaptGridView)
        mHottestGridAdapter = MarketHottestGridAdater(this@MarketFragment.context!!)
        mHottestGridView.run {
            mHottestGridAdapter.setOnRecyclerViewItemClickListener(object :
                MarketHottestGridAdater.OnGridRecyclerViewItemClickListener {
                override fun onItemClick(
                    adapter: MarketHottestRecyAdapter,
                    view: View,
                    position: Int,
                    data: TestMarketHottestRecyEntity
                ) {
                    ToastUtil.showShortToast(this@MarketFragment.context!!, data.text)
                }
            })
            adapter = mHottestGridAdapter
        }
        mMarketListAdapter.addHeaderView(mHottestContainerView, 3)
        //-------------------------------------------------------批发商圈
        mBusinessContainerView = LayoutInflater.from(context).inflate(
            R.layout.layout_market_business,
            layout_list_refresh_recycView,
            false
        ) as View
        mBusinessGridView = mBusinessContainerView.findViewById(R.id.layout_market_business_autoAdaptGridView)
        mBussinessAdapter = MarketBusinessAdapter(this@MarketFragment.context!!)
        mBusinessGridView.run {
            adapter = mBussinessAdapter

        }
        mMarketListAdapter.addHeaderView(mBusinessContainerView, 4)
        //------------------------------------------------------状态栏
        status_bar.run {
            StatusBarCompat.translucentStatusBar(this@MarketFragment.activity!!)
              setBackgroundColor(ContextCompat.getColor(this@MarketFragment.context!!, R.color.transparent))
        }
        //------------------------------------------------------refresh 刷新
        layout_list_refresh_layout.run {

        }
    }
}
