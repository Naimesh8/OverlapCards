package com.example.overlapcards.utils

import android.content.Context
import android.util.DisplayMetrics

object DeviceUtil {

    var offsetMargin = 0
    //top margin for recyclerview
    private var topMargin = 100
    //we can assign any value we want for card which overlapped by another card , here i am assigning 118
    private var overlappedCardHeight = 118

    fun updateOffsetMargin(context: Context){
        offsetMargin = (getScale(context) * overlappedCardHeight).toInt()
    }

    private fun getScreenHeight(context: Context): Int {
        return context.resources.displayMetrics.heightPixels
    }

    fun convertDpToPx(context: Context, dp: Float): Float {
        return dp * getScale(context)
    }

    fun convertPxToDp(px: Float, context: Context): Float {
        return px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    private fun getStatusBarHeight(context: Context?): Int {
        var result = 0
        context?.let {
            val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = context.resources.getDimensionPixelSize(resourceId)
            }
        }
        return result
    }

    fun getScale(context: Context): Float{
        return context.resources.displayMetrics.density
    }

    fun getHeightOfBaseView(context: Context): Int{
        val usableHeightInPx: Int = getScreenHeight(context) - getStatusBarHeight(context)
        val topMarginInPx = convertDpToPx(context, topMargin.toFloat()).toInt()
        val availableHeightInPx = usableHeightInPx - topMarginInPx
        val availableHeightInDp = convertPxToDp(availableHeightInPx.toFloat(), context).toInt()
        val radiusMargin = 30
        return availableHeightInDp + radiusMargin
    }



}