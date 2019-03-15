package com.kiss.fittingroom.weight.banner

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.kiss.fittingroom.entity.TestMarketBannerEntity
import com.zhouwei.mzbanner.holder.MZViewHolder
import android.view.LayoutInflater
import com.kiss.fittingroom.R


/**
 *
 * @ProjectName:    FittingRoom
 * @Package:        com.kiss.fittingroom.weight.banner
 * @ClassName:      MarketBannerViewHolder
 * @Description:     java类作用描述
 * @Author:         lxy
 * @CreateDate:     2019/3/15 15:50
 * @UpdateUser:     lxy
 * @UpdateDate:     2019/3/15 15:50
 * @UpdateRemark:
 * @Version:        1.0
 */
class MarketBannerViewHolder : MZViewHolder<TestMarketBannerEntity> {
    private lateinit var mImageView: ImageView
    private lateinit var mTextView: TextView
    override fun onBind(context: Context?, position: Int, item: TestMarketBannerEntity?) {
        // 数据绑定
        Glide.with(context!!).load(item!!.image).into(mImageView)
        mTextView.text = item.title
    }

    override fun createView(context: Context?): View {
        // 返回页面布局
        val view = LayoutInflater.from(context).inflate(R.layout.item_market_banner, null)
        mImageView = view.findViewById(R.id.item_market_banner_image)
        mTextView = view.findViewById(R.id.item_market_banner_title)
        return view
    }
}