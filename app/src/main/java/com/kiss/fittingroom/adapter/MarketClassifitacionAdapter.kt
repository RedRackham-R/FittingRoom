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

/**
 * @Description:     商城分类
 */
class MarketClassifitacionAdapter(context:Context):BaseAdapter() {
    private var mData :ArrayList<TestMarketClassificationEntity>  = ArrayList()
    private val mContext = context

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var itemView = convertView
        if (itemView  == null){
            itemView = LayoutInflater.from(mContext).inflate(R.layout.item_market_classification, null) as View
        }
        val textView = itemView.findViewById<TextView>(R.id.item_classification_tv)
        val imageView = itemView.findViewById<ImageView>(R.id.item_classification_img)
        textView.text = mData[position].text
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

    fun setNewData(data:ArrayList<TestMarketClassificationEntity>){
        mData.clear()
        if (!data.isNullOrEmpty()){
            mData.addAll(data)
        }
        notifyDataSetChanged()
    }
}