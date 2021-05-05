package com.example.overlapcards.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.overlapcards.R
import com.example.overlapcards.databinding.ActivityMainBinding
import com.example.overlapcards.utils.DeviceUtil
import com.example.overlapcards.view.adapters.SlideUpViewAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding : ActivityMainBinding
    private lateinit var mSlideUpViewAdapter: SlideUpViewAdapter
    private var mCurrLayer = 1 //to maintain count of current stack layer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        supportActionBar?.hide()
        initAdapter()
        activityMainBinding.bottomButton.setOnClickListener {
            addLayer(++mCurrLayer)
            activityMainBinding.bottomButton.text = "Bottom_cta for position = $mCurrLayer"
            if(mCurrLayer == SlideUpViewAdapter.MAX_ITEMS_COUNT){
                activityMainBinding.bottomButton.isClickable = false
            }
        }
    }

    override fun onBackPressed() {
        if (mCurrLayer > 1) {
            removeLayer()
        } else {
            super.onBackPressed()
        }
    }

    private val itemClickListener =
        View.OnClickListener { view ->
            val position = view.tag as Int
            if (position == mCurrLayer - 2) {
                removeLayer()
            }
        }

    private fun initAdapter() {
        DeviceUtil.updateOffsetMargin(this)
        val layoutManager: LinearLayoutManager = object : LinearLayoutManager(this) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        activityMainBinding.recyclerView.layoutManager = layoutManager
        var baseViewHeightDp = DeviceUtil.getHeightOfBaseView(this)
        var baseViewHeightPx = DeviceUtil.convertDpToPx(this,baseViewHeightDp.toFloat())
        activityMainBinding.recyclerView.addItemDecoration(ItemDecorator(DeviceUtil.offsetMargin,baseViewHeightPx.toInt()))
        mSlideUpViewAdapter = SlideUpViewAdapter(this, itemClickListener)
        activityMainBinding.recyclerView.adapter = mSlideUpViewAdapter
    }

    private fun addLayer(nextLayer: Int) {
        mSlideUpViewAdapter.updateTotalCount(nextLayer)
        mSlideUpViewAdapter.notifyItemInserted(nextLayer - 1)
    }

    private fun removeLayer() {
        mSlideUpViewAdapter.updateTotalCount(--mCurrLayer)
        mSlideUpViewAdapter.notifyItemRemoved(mCurrLayer)
        activityMainBinding.bottomButton.text = "Bottom_cta For position = $mCurrLayer"
        if(mCurrLayer == SlideUpViewAdapter.MAX_ITEMS_COUNT-1){
            activityMainBinding.bottomButton.isClickable = true
        }
    }

}