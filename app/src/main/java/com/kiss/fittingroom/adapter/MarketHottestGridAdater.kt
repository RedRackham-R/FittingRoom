package com.kiss.fittingroom.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.kiss.fittingroom.R
import com.kiss.fittingroom.entity.TestMarketHottestGirdEntity
import com.kiss.fittingroom.entity.TestMarketHottestRecyEntity

/**
 * @Description:    热门部分
 */
class MarketHottestGridAdater(context:Context) :BaseGridAdapter<TestMarketHottestGirdEntity>() {
    private val mContext = context


    private var mGridRecyclerItemCliclListener:OnGridRecyclerViewItemClickListener? = null

    @SuppressLint("InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var itemView = convertView
        if (itemView  == null){
            itemView = LayoutInflater.from(mContext).inflate(R.layout.item_market_hottest_grid, null) as View
        }
        val textView:TextView = itemView.findViewById(R.id.item_market_hottest_textView)
        val recyclerView :RecyclerView = itemView.findViewById(R.id.item_market_hottest_recyclerView)
        textView.text = getData()[position].text
        textView.setBackgroundColor(getData()[position].color)
        textView.setTextColor(ContextCompat.getColor(mContext,R.color.color_white))
        val mAdapter = MarketHottestRecyAdapter()
        recyclerView.run {
            val linearLayoutManager = LinearLayoutManager(context)
            linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = linearLayoutManager
            adapter = mAdapter
        }

        mAdapter.run {
            setNewData(this@MarketHottestGridAdater.getData()[position].data)
            setOnItemClickListener { adapter, view, position ->
                if (mGridRecyclerItemCliclListener != null){
                    mGridRecyclerItemCliclListener!!.onItemClick(mAdapter,view,position,mAdapter.data[position])
                }
            }
        }
        return itemView
    }



    /**
     * 设置监听器 监听GridView中的RecyclerView点击事件
     */
    fun setOnRecyclerViewItemClickListener(listener:OnGridRecyclerViewItemClickListener){
        mGridRecyclerItemCliclListener = listener
    }

    /**
     * GridView中的RecyclerView点击监听器
     */
    interface OnGridRecyclerViewItemClickListener{
         fun onItemClick(adapter: MarketHottestRecyAdapter, view: View, position: Int,data:TestMarketHottestRecyEntity)
    }

}