package com.kiss.fittingroom.weight.gridView

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.GridView
import android.widget.AbsListView
import java.util.jar.Attributes


/**
 *
 * @ProjectName:    wanandroidkt
 * @Package:        com.kiss.fittingroom.weight.gridView
 * @ClassName:      AutoAdaptGridView
 * @Description:     自适应GridView
 * @Author:         lxy
 * @CreateDate:     2019/3/11 15:22
 * @UpdateUser:     lxy
 * @UpdateDate:     2019/3/11 15:22
 * @UpdateRemark:
 * @Version:        1.0
 */
class AutoAdaptGridView : GridView {
    constructor(context: Context) : super(context)

    constructor(context: Context,attributes: AttributeSet):super(context,attributes)

    constructor(context:Context,attrs:AttributeSet, defStyleAttr:Int):super(context,attrs,defStyleAttr)


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val heightSpec: Int = if (layoutParams.height == AbsListView.LayoutParams.WRAP_CONTENT) {
            View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE shr 2, View.MeasureSpec.AT_MOST)
        } else {
            heightMeasureSpec
        }
        super.onMeasure(widthMeasureSpec, heightSpec)
    }

}