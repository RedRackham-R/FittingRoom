package com.kiss.fittingroom.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.MultipleItemRvAdapter
import com.kiss.fittingroom.R

/**
 * 商城列表
 */
class MarketListAdapter :BaseQuickAdapter<String,BaseViewHolder>(R.layout.item_test){
    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper!!.setText(R.id.item_test_tv,item)
    }

}