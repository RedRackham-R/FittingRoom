package com.kiss.fittingroom.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.kiss.fittingroom.R
import com.kiss.fittingroom.entity.TestMarketClassificationEntity
import com.kiss.fittingroom.entity.TestMarketLiveEntity

/**
 *Market直播Grid
 */
class MarketLiveAdapter(context: Context) :BaseAdapter(){
    private var mData :ArrayList<TestMarketLiveEntity>  = ArrayList()
    private val mContext = context

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var itemView = convertView
        if (itemView  == null){
            itemView = LayoutInflater.from(mContext).inflate(R.layout.item_market_live, null) as View
        }
        val textView1 = itemView.findViewById<TextView>(R.id.item_market_live_tv1)
        val textView2 = itemView.findViewById<TextView>(R.id.item_market_live_tv2)
        val imageView = itemView.findViewById<ImageView>(R.id.item_market_live_img)

        textView1.text = mData[position].text1
        textView2.text = mData[position].text2
        Glide.with(mContext).load(mData[position].img).into(imageView)
        return itemView
    }

    override fun getItem(position: Int): Any {
        return mData[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return mData.size
    }

    fun setNewData(data:ArrayList<TestMarketLiveEntity>){
        mData.clear()
        if (!data.isNullOrEmpty()){
            mData.addAll(data)
        }
        notifyDataSetChanged()
    }
}