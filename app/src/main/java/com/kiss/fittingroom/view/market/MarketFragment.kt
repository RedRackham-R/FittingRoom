package com.kiss.fittingroom.view.market

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.*
import com.bumptech.glide.Glide
import com.kiss.fittingroom.R
import com.kiss.fittingroom.adapter.*
import com.kiss.fittingroom.base.BaseFragment
import com.kiss.fittingroom.entity.*
import com.kiss.fittingroom.utils.StatusBarCompat
import com.kiss.fittingroom.utils.ToastUtil
import com.kiss.fittingroom.weight.banner.MarketBannerViewHolder
import com.kiss.fittingroom.weight.gridView.AutoAdaptGridView
import com.zhouwei.mzbanner.MZBannerView
import kotlinx.android.synthetic.main.layout_list_refresh.*
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



    private lateinit var mBanner: MZBannerView<TestMarketBannerEntity> //banner
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

    private lateinit var mSquareContainerView: View//同行求货广场
    private lateinit var mSquareViewFlipper: ViewFlipper

    private lateinit var mHeaderSwitchContainerView: View// 添加进recyclerView的时段选择
    private lateinit var mHeaderSwitchRadioGroup: RadioGroup
    private lateinit var mHeaderSwitchRadioButtonToday: RadioButton//今天
    private lateinit var mHeaderSwitchRadioButtonYesterday: RadioButton//昨天
    private lateinit var mHeaderSwitchRadioButtonWeek: RadioButton//这周

    private lateinit var mStickSwitchContainerView: View//粘性头部时段选择
    private lateinit var mStickSwitchRadioGroup: RadioGroup
    private lateinit var mStickSwitchRadioButtonToday: RadioButton//今天
    private lateinit var mStickSwitchRadioButtonYesterday: RadioButton//昨天
    private lateinit var mStickSwitchRadioButtonWeek: RadioButton//这周

    private var isFristLoad = true
    companion object {
        const val SWITCH_TYPE_TODAY = 0//选择今天
        const val SWITCH_TYPE_YESTERDAY = 1//选择昨天
        const val SWITCH_TYPE_WEEK = 2//选择这周

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
        val data: ArrayList<TestMarketListEntity> = ArrayList()
        for (index in 0..50) {
            data.add(
                TestMarketListEntity(
                    "1小时前 激烈**海胆下的订单卖家发货了",
                    "有爱的小店$index",
                    R.drawable.icon_head,
                    "·当前离线",
                    R.drawable.icon_test_goods_1,
                    "单售：￥88",
                    R.drawable.icon_test_goods_2,
                    "单售：￥120",
                    R.drawable.icon_test_goods_3,
                    "单售：￥220"

                )
            )
        }
        mMarketListAdapter.setNewData(data)
        //--------------Banner数据
        // 设置数据
        val bannerData :ArrayList<TestMarketBannerEntity> = ArrayList()
        for (index in 0..4){
            when(index){
                0->{  bannerData.add(TestMarketBannerEntity(R.drawable.test_img_1, "测试banner标题$index"))}
                1->{  bannerData.add(TestMarketBannerEntity(R.drawable.test_img_2, "测试banner标题$index"))}
                2->{  bannerData.add(TestMarketBannerEntity(R.drawable.test_img_3, "测试banner标题$index"))}
                else->{  bannerData.add(TestMarketBannerEntity(R.drawable.test_img_3, "测试banner标题$index"))}
            }
        }
        mBanner.setPages(bannerData) {
            MarketBannerViewHolder()
        }


        //--------------分类数据
        val classifitacinDataList: ArrayList<TestMarketClassificationEntity> = ArrayList()
        for (index in 0..9) {
            when(index){
                0->{ classifitacinDataList.add(TestMarketClassificationEntity("分类$index", R.drawable.icon_classification0))}
                1->{ classifitacinDataList.add(TestMarketClassificationEntity("分类$index", R.drawable.icon_classification1))}
                2->{ classifitacinDataList.add(TestMarketClassificationEntity("分类$index", R.drawable.icon_classification2))}
                3->{ classifitacinDataList.add(TestMarketClassificationEntity("分类$index", R.drawable.icon_classification3))}
                4->{ classifitacinDataList.add(TestMarketClassificationEntity("分类$index", R.drawable.icon_classification4))}
                5->{ classifitacinDataList.add(TestMarketClassificationEntity("分类$index", R.drawable.icon_classification5))}
                6->{ classifitacinDataList.add(TestMarketClassificationEntity("分类$index", R.drawable.icon_classification6))}
                7->{ classifitacinDataList.add(TestMarketClassificationEntity("分类$index", R.drawable.icon_classification7))}
                8->{ classifitacinDataList.add(TestMarketClassificationEntity("分类$index", R.drawable.icon_classification8))}
                9->{ classifitacinDataList.add(TestMarketClassificationEntity("分类$index", R.drawable.icongif_classification))}
            }
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
        for (index in 0..2) {
            businessData.add(
                TestMarketBusinessEntity(
                    R.drawable.test_img_1,
                    "测试标题$index",
                    "4096",
                    "6589",
                    "测试说明测试说明测试说明测试说明测试说明测试说明测试说明测试说明测试说明测试说明测试说明测试说明" +
                            "测试说明测试说明测试说明测试说明测试说明测试说明测试说明测试说明测试说明测试说明测试说明测试说明测",
                    "40.72万人在线",
                    "出货53.58万款",
                    "4689评论"
                )
            )
        }
        mBussinessAdapter.setNewData(businessData)

        //--------------求货广场
        val squareDataList:ArrayList<TestMarketSquareEntity> = ArrayList()
        for (index in 0..9){
            squareDataList.add(TestMarketSquareEntity("用户名$index","测试说明测试说明测试说明测试说明测试说明测试说明测",R.drawable.icon_head))
            val tempSquareItemView = LayoutInflater.from(this@MarketFragment.context!!).inflate(R.layout.item_market_square,mSquareViewFlipper,false)
            val imageView : ImageView = tempSquareItemView.findViewById(R.id.item_market_square_imageView)
            val nameTv: TextView = tempSquareItemView.findViewById(R.id.item_market_square_name)
            val textTv:TextView = tempSquareItemView.findViewById(R.id.item_market_square_text)
            val releaseBtn :View = tempSquareItemView.findViewById(R.id.item_market_square_release)
            Glide.with(this@MarketFragment.context!!).load(squareDataList[index].img).into(imageView)
            nameTv.text = squareDataList[index].userName
            textTv.text = squareDataList[index].text
            mSquareViewFlipper.addView(tempSquareItemView,index)
        }

    }


    /**
     * 初始化各个view
     */
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
                    status_bar.setBackgroundColor(
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
            addOnScrollListener(object : RecyclerView.OnScrollListener() { //添加滚动箭筒
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
                        if (isFristLoad){
                            isFristLoad = false
                        }else{
                            Glide.with(this@MarketFragment.context!!).load(R.drawable.icon_logo_white).into(layout_market_bar_logo) //设置logo
                        }
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
                        Glide.with(this@MarketFragment.context!!).load(R.drawable.icon_logo_them ).into(layout_market_bar_logo) //设置logo
                    }
                    if (recyclerViewScrollOffset >= getAllHeaderHeight()) {//判断偏移量是否超过所有header的高度
                        layout_market_bar_switch.visibility = View.VISIBLE
                    } else {
                        layout_market_bar_switch.visibility = View.INVISIBLE
                    }
                }
            })
        }

        //------------------------------------------------------banner
        mBanner = LayoutInflater.from(context).inflate(
            R.layout.layout_market_banner,
            layout_list_refresh_recycView,
            false
        ) as MZBannerView<TestMarketBannerEntity>
        mBanner.run {
            viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                //获取View高度
                override fun onGlobalLayout() {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                    mBannerHeight = mBanner.measuredHeight
                }
            })
            setIndicatorVisible(false)
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
        //-------------------------------------------------------求货广场
        mSquareContainerView = LayoutInflater.from(context).inflate(
            R.layout.layout_market_square,
            layout_list_refresh_recycView,
            false
        ) as View
        mSquareViewFlipper = mSquareContainerView.findViewById(R.id.layout_market_square_viewFlipper)
        mMarketListAdapter.addHeaderView(mSquareContainerView, 5)

        //-------------------------------------------------------时段选择 (添加进RecyclerView)
        mHeaderSwitchContainerView = LayoutInflater.from(context).inflate(
            R.layout.layout_market_switch,
            layout_list_refresh_recycView,
            false
        ) as View
        mHeaderSwitchRadioGroup = mHeaderSwitchContainerView.findViewById(R.id.item_market_switch_radiogoup)
        mHeaderSwitchRadioButtonToday = mHeaderSwitchRadioGroup.findViewById(R.id.item_market_switch_today)
        mHeaderSwitchRadioButtonYesterday = mHeaderSwitchRadioGroup.findViewById(R.id.item_market_switch_yesterday)
        mHeaderSwitchRadioButtonWeek = mHeaderSwitchRadioGroup.findViewById(R.id.item_market_switch_week)
        mHeaderSwitchRadioGroup.run {
            setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.item_market_switch_today -> {//今天
                        switchBusniessTime(SWITCH_TYPE_TODAY)
                    }
                    R.id.item_market_switch_yesterday -> {//昨天
                        switchBusniessTime(SWITCH_TYPE_YESTERDAY)
                    }

                    R.id.item_market_switch_week -> { //这周
                        switchBusniessTime(SWITCH_TYPE_WEEK)
                    }
                }
            }
        }
        mMarketListAdapter.addHeaderView(mHeaderSwitchContainerView, 6)


        //-------------------------------------------------------时段选择 (粘性头部)
        mStickSwitchContainerView = layout_market_bar_switch
        mStickSwitchRadioGroup = mStickSwitchContainerView.findViewById(R.id.item_market_switch_radiogoup)
        mStickSwitchRadioButtonToday = mStickSwitchRadioGroup.findViewById(R.id.item_market_switch_today)
        mStickSwitchRadioButtonYesterday = mStickSwitchRadioGroup.findViewById(R.id.item_market_switch_yesterday)
        mStickSwitchRadioButtonWeek = mStickSwitchRadioGroup.findViewById(R.id.item_market_switch_week)
        mStickSwitchRadioGroup.run {
            setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.item_market_switch_today -> {//今天
                        switchBusniessTime(SWITCH_TYPE_TODAY)
                        scrollToBusniessTop()
                    }
                    R.id.item_market_switch_yesterday -> {//昨天
                        switchBusniessTime(SWITCH_TYPE_YESTERDAY)
                        scrollToBusniessTop()
                    }

                    R.id.item_market_switch_week -> { //这周
                        switchBusniessTime(SWITCH_TYPE_WEEK)
                        scrollToBusniessTop()
                    }
                }
            }
        }

        //------------------------------------------------------状态栏
        status_bar.run {
            StatusBarCompat.translucentStatusBar(this@MarketFragment.activity!!)
            setBackgroundColor(ContextCompat.getColor(this@MarketFragment.context!!, R.color.transparent))
        }
        //------------------------------------------------------refresh 刷新
        layout_list_refresh_layout.run {

        }
    }


    /**
     * 返回所有头布局的高度
     */
    private fun getAllHeaderHeight(): Int {
        return mBanner.measuredHeight + mClassificationGridView.measuredHeight + mLiveContainerView.measuredHeight + mHottestContainerView.measuredHeight + mBusinessContainerView.measuredHeight + mSquareContainerView.measuredHeight + layout_market_bar_switch.measuredHeight - mToolbarHeight - StatusBarCompat.getStatusBarHeight(
            this@MarketFragment.context!!
        )
    }

    /**
     * 滚动到商户的顶端（时间选择）
     */
    private fun scrollToBusniessTop() {
        layout_list_refresh_recycView.scrollBy(0, getAllHeaderHeight() - recyclerViewScrollOffset)
    }

    /**
     * 选择商家店铺的时间
     */
    private fun switchBusniessTime(switchType: Int) {
        when (switchType) {
            SWITCH_TYPE_TODAY -> {
                if (!mHeaderSwitchRadioButtonToday.isChecked) {
                    mHeaderSwitchRadioButtonToday.isChecked = true
                }
                if (!mStickSwitchRadioButtonToday.isChecked) {
                    mStickSwitchRadioButtonToday.isChecked = true
                }
            }
            SWITCH_TYPE_YESTERDAY -> {
                if (!mHeaderSwitchRadioButtonYesterday.isChecked) {
                    mHeaderSwitchRadioButtonYesterday.isChecked = true
                }
                if (!mStickSwitchRadioButtonYesterday.isChecked) {
                    mStickSwitchRadioButtonYesterday.isChecked = true
                }
            }
            SWITCH_TYPE_WEEK -> {
                if (!mHeaderSwitchRadioButtonWeek.isChecked) {
                    mHeaderSwitchRadioButtonWeek.isChecked = true
                }
                if (!mStickSwitchRadioButtonWeek.isChecked) {
                    mStickSwitchRadioButtonWeek.isChecked = true
                }
            }
        }

    }


}
