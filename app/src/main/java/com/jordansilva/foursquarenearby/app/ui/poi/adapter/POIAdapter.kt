package com.jordansilva.foursquarenearby.app.ui.poi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.jordansilva.foursquarenearby.app.BR
import com.jordansilva.foursquarenearby.app.R
import com.jordansilva.foursquarenearby.app.model.POIView
import com.jordansilva.foursquarenearby.app.util.OnItemClickViewListener

class POIRecyclerViewAdapter(private var data: List<POIView>?,
                             private val mListener: OnItemClickViewListener<POIView>) :
        RecyclerView.Adapter<POIRecyclerViewAdapter.ViewHolder>(), Filterable {

    private val mOnClickListener: View.OnClickListener
    private val filteredData: ArrayList<POIView> = arrayListOf()

    init {
        updateData(data)

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
        val item = filteredData[position]
        holder.bindView(item)
        holder.itemView.tag = item
        holder.itemView.setOnClickListener(mOnClickListener)
    }

    override fun getItemCount(): Int = filteredData.size
    override fun getFilter(): Filter = AdapterFilter(this, data!!)

    fun updateData(items: List<POIView>?) {
        data = items ?: arrayListOf()
        filteredData.clear()
        filteredData.addAll(data!!)
        notifyDataSetChanged()
    }

    private fun filterData(items: List<POIView>?) {
        filteredData.clear()
        items?.let { filteredData.addAll(it) }
        for (i in 0 until filteredData.size)
            notifyItemInserted(0)
    }

    inner class ViewHolder(val view: ViewDataBinding) : RecyclerView.ViewHolder(view.root) {

        fun bindView(item: POIView) {
            view.setVariable(BR.POI, item)
        }
    }

    inner class AdapterFilter(private val adapter: POIRecyclerViewAdapter,
                              private val completeList: List<POIView>) : Filter() {

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            results?.let {
                adapter.filterData(results.values as List<POIView>)
                adapter.notifyDataSetChanged()
            }
        }

        override fun performFiltering(constraint: CharSequence?): FilterResults {

            val filterResults = FilterResults()

            val list = constraint?.let {text ->
                completeList.filter {
                    it.name.contains(text, true)
                            || it.location.contains(text, true)
                            || it.category?.contains(text, true) ?: false
                }
            } ?: completeList

            filterResults.values = list
            filterResults.count = list.size
            return filterResults
        }


    }
}
