package com.kiss.fittingroom.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kiss.fittingroom.R
import com.kiss.fittingroom.entity.TestMarketListEntity

/**
 * 商城列表
 */
class MarketListAdapter :BaseQuickAdapter<TestMarketListEntity,BaseViewHolder>(R.layout.item_market_list){
    override fun convert(helper: BaseViewHolder?, item: TestMarketListEntity?) {
        helper!!.setText(R.id.item_market_list_history,item!!.histroy)
            .setText(R.id.item_market_list_shopName,item.shopName)

        val goodImage_1:ImageView  = helper.getView(R.id.item_market_list_good_img_1)
        val goodImage_2:ImageView  = helper.getView(R.id.item_market_list_good_img_2)
        val goodImage_3:ImageView  = helper.getView(R.id.item_market_list_good_img_3)

        Glide.with(mContext).load(item.goodsImage_1).into(goodImage_1)
        Glide.with(mContext).load(item.goodsImage_2).into(goodImage_2)
        Glide.with(mContext).load(item.goodsImage_3).into(goodImage_3)
    }

}