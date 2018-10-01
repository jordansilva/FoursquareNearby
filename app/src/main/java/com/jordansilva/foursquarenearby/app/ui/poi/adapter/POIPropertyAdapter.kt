package com.jordansilva.foursquarenearby.app.ui.poi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.jordansilva.foursquarenearby.app.R
import com.jordansilva.foursquarenearby.app.util.OnItemClickViewListener
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.IIcon
import kotlinx.android.synthetic.main.row_poi_detail_property.view.*

class POIPropertyAdapter(items: List<POIProperty>?, mListener: OnItemClickViewListener<POIProperty>) :
        RecyclerView.Adapter<POIPropertyAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener
    private var data = listOf<POIProperty>()

    init {
        updateData(items)

        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as POIProperty
            mListener.onClickItem(v, item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.row_poi_detail_property, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bindView(item)
        holder.itemView.tag = item
        holder.itemView.setOnClickListener(mOnClickListener)
    }

    override fun getItemCount(): Int = data.size

    fun updateData(items: List<POIProperty>?) {
        data = items ?: arrayListOf()
        notifyDataSetChanged()
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(item: POIProperty) {
            view.textProperty.text = item.name
            view.imageProperty.icon = IconicsDrawable(view.context).icon(item.image)
                    .color(ContextCompat.getColor(view.context, R.color.dark_gray))
                    .sizeDp(14)
        }
    }

    data class POIProperty(var name: String, var type: String, var image: IIcon? = null)
}
