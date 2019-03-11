package com.kiss.fittingroom.adapter

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kiss.fittingroom.R
import com.kiss.fittingroom.entity.TestMarketHottestRecyEntity

/**
 *
 * @ProjectName:    FittingRoom
 * @Package:        com.kiss.fittingroom.adapter
 * @ClassName:      MarketHottestRecyAdapter
 * @Description:     java类作用描述
 * @Author:         lxy
 * @CreateDate:     2019/3/11 17:10
 * @UpdateUser:     lxy
 * @UpdateDate:     2019/3/11 17:10
 * @UpdateRemark:
 * @Version:        1.0
 */
class MarketHottestRecyAdapter :BaseQuickAdapter<TestMarketHottestRecyEntity,BaseViewHolder>(R.layout.item_market_hottest_recy) {
    override fun convert(helper: BaseViewHolder?, item: TestMarketHottestRecyEntity?) {
        helper!!.setText(R.id.item_market_hotest_recy_textView,item!!.text)
        val imageView:ImageView = helper.getView(R.id.item_market_hotest_recy_img)
        Glide.with(mContext).load(item.img).into(imageView)
    }
}