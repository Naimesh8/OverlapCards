package com.example.overlapcards.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class ItemDecorator(private val mSpace: Int,private val mViewHeight: Int) : ItemDecoration() {

    private val mScrollOffsetPx = 10

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        //Note = assuming max layer count will be 4 only
        if (position != 0) {
            if (position == 1) {
                outRect.top = -(mViewHeight - position * mSpace - mScrollOffsetPx)
            } else if (position == 2) {
                outRect.top = -(mViewHeight - position * mSpace - mScrollOffsetPx)
            } else if (position == 3) {
                outRect.top = -(mViewHeight - position * mSpace - mScrollOffsetPx)
            } else if (position == 4) {
                outRect.top = -(mViewHeight - position * mSpace - mScrollOffsetPx)
            }
        }
    }

}