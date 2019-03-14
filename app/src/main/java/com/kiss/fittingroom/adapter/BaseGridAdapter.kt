package com.kiss.fittingroom.adapter

import android.widget.BaseAdapter

/**
 *
 * @ProjectName:    FittingRoom
 * @Package:        com.kiss.fittingroom.adapter
 * @ClassName:      BaseGridAdapter
 * @Description:    GridViewAdapter简单封装
 * @Author:         lxy
 * @CreateDate:     2019/3/14 13:51
 * @UpdateUser:     lxy
 * @UpdateDate:     2019/3/14 13:51
 * @UpdateRemark:
 * @Version:        1.0
 */
 abstract class BaseGridAdapter<T> :BaseAdapter() {


    private val  mData :ArrayList<T> = ArrayList()



    override fun getItem(position: Int): T {
        return mData[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return mData.size
    }

    fun setNewData(data:ArrayList<T>){
        mData.clear()
        if (!data.isNullOrEmpty()){
            mData.addAll(data)
            notifyDataSetChanged()
        }else{
            throw NullPointerException("数据为空")
        }

    }

    fun addData(data:ArrayList<T>){
        if (!data.isNullOrEmpty()){
            mData.addAll(data)
            notifyDataSetChanged()
        }else{
            throw NullPointerException("数据为空")
        }
    }


    fun getData():ArrayList<T>{
        return mData
    }
}