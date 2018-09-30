package com.jordansilva.foursquarenearby.app.ui.poi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.jordansilva.foursquarenearby.app.R
import com.jordansilva.foursquarenearby.app.util.loadUrl

class SliderImageAdapter(private val context: Context,
                          private var data: List<String>) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int = data.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.row_imageview, container, false)

        if (data[position].isNotBlank())
            (view as ImageView).loadUrl(data[position])

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as View)
    }

    fun updateData(banners: List<String>?) {
        data = banners?.let { it } ?: arrayListOf()
        notifyDataSetChanged()
    }


}