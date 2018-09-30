package com.jordansilva.foursquarenearby.app.ui.poi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.jordansilva.foursquarenearby.app.BR
import com.jordansilva.foursquarenearby.app.R
import com.jordansilva.foursquarenearby.app.model.POIView
import com.jordansilva.foursquarenearby.app.util.OnItemClickViewListener

class POIRecyclerViewAdapter(private val data: List<POIView>,
                             private val mListener: OnItemClickViewListener<POIView>)
    : RecyclerView.Adapter<POIRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as POIView
            mListener.onClickItem(v, item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding: ViewDataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.row_poi_view, parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bindView(item)
        holder.itemView.tag = item
        holder.itemView.setOnClickListener(mOnClickListener)
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(val view: ViewDataBinding) : RecyclerView.ViewHolder(view.root) {

        fun bindView(item: POIView) {
            view.setVariable(BR.POI, item)
        }
    }
}
