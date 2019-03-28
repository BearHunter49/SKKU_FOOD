package com.example.skku_food.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class menu_rcl_itemdecor(private val mSpanCount: Int, private val mItemSize: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State) {

            val position = parent.getChildLayoutPosition(view)
            val column = position % mSpanCount
            val parentWidth = parent.width
            val spacing = (parentWidth - mItemSize * mSpanCount) / (mSpanCount + 1)
            outRect.left = spacing - column * spacing / mSpanCount
            outRect.right = (column + 1) * spacing / mSpanCount

            if (position < mSpanCount) {
                outRect.top = spacing
            }
            outRect.bottom = spacing
    }
}