package com.kiss.fittingroom.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.kiss.fittingroom.R
import com.kiss.fittingroom.entity.TestMarketBusinessEntity

/**
 *
 * @ProjectName:    FittingRoom
 * @Package:        com.kiss.fittingroom.adapter
 * @ClassName:      MarketBusinessAdapter
 * @Description:     商圈
 * @Author:         lxy
 * @CreateDate:     2019/3/14 11:59
 * @UpdateUser:     lxy
 * @UpdateDate:     2019/3/14 11:59
 * @UpdateRemark:
 * @Version:        1.0
 */
class MarketBusinessAdapter(context: Context) : BaseGridAdapter<TestMarketBusinessEntity>() {
    private val mContext: Context = context
    @SuppressLint("InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var itemView = convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(mContext).inflate(R.layout.item_market_business, null) as View
        }

        val img: ImageView = itemView.findViewById(R.id.item_market_business_img)//图片
        val rankTv:TextView = itemView.findViewById(R.id.item_market_business_rank)//排名
        val titleTv: TextView = itemView.findViewById(R.id.item_market_business_title)//标题
        val weekTv: TextView = itemView.findViewById(R.id.item_market_business_week)//每周爆款
        val wechatTv: TextView = itemView.findViewById(R.id.item_market_business_wechat)//微信爆款
        val contentTv: TextView = itemView.findViewById(R.id.item_market_business_content)//说明
        val onLineTv: TextView = itemView.findViewById(R.id.item_market_business_online)//在线
        val saleTv: TextView = itemView.findViewById(R.id.item_market_business_sale)//出货
        val commentTv: TextView = itemView.findViewById(R.id.item_market_business_comment)//评论

        Glide.with(mContext).load(img).into(img)
        titleTv.text = getData()[position].title
        weekTv.text = getData()[position].week
        wechatTv.text = getData()[position].wechat
        contentTv.text = getData()[position].content
        onLineTv.text = getData()[position].onLine
        saleTv.text = getData()[position].sale
        commentTv.text = getData()[position].comment

        when(position){
            0->{rankTv.text = "No.1"}
            1->{rankTv.text = "No.2"}
            2->{rankTv.text = "No.3"}
            else->{rankTv.text = "No.N"}
        }
        return itemView
    }

}