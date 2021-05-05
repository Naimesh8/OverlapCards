package com.example.overlapcards.view.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.overlapcards.R
import com.example.overlapcards.utils.DeviceUtil
import java.util.*

internal class SlideUpViewAdapter(
    private val mContext: Context,
    private val onItemClickListener: View.OnClickListener
) : RecyclerView.Adapter<SlideUpViewAdapter.ItemViewHolder>() {

    private var totalCount = 1 //total items in recycler view
    private var mViewHeight = 0 //holds height of the base layer

    init {
        mViewHeight = DeviceUtil.getHeightOfBaseView(mContext)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val view: View = LayoutInflater.from(mContext).inflate(R.layout.row_item_view, parent, false)
        return ItemViewHolder(view)
    }


    override fun onBindViewHolder(
        holder: ItemViewHolder,
        position: Int
    ) {
        holder.textView.text = "Position = $position"
        val rand = Random()
        holder.itemView.setBackgroundResource(R.drawable.row_item_background)
        val drawable = holder.itemView.background as GradientDrawable
        drawable.setColor(Color.rgb( rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)))
        holder.itemView.tag = position

        val pxHeightOffset = DeviceUtil.offsetMargin.toFloat()
        val heightOffset: Float = DeviceUtil.convertPxToDp(position * pxHeightOffset, mContext)
        setHeightOfChildView(holder.itemView, heightOffset)
        showAnimation(holder.itemView)
    }

    private fun setHeightOfChildView(view: View, h: Float) {
        val scale = DeviceUtil.getScale(mContext)
        val dpHeight = (mViewHeight - h).toInt()
        val dpHeightInPx = (dpHeight * scale).toInt()
        val layoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpHeightInPx)
        view.layoutParams = layoutParams
    }

    fun updateTotalCount(count: Int) {
        totalCount = count
    }

    override fun getItemCount(): Int {
        return totalCount
    }

    private fun showAnimation(view: View) {
        val animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_from_bottom)
        animation.interpolator = AccelerateDecelerateInterpolator()
        view.animation = animation
        animation.start()
    }

    internal inner class ItemViewHolder(itemView: View) : ViewHolder(itemView) {
        var textView: TextView
        init {
            textView = itemView.findViewById<View>(R.id.text) as TextView
            itemView.setOnClickListener(onItemClickListener)
        }
    }

    companion object {
        const val MAX_ITEMS_COUNT = 3; //total layers we can add
    }
}