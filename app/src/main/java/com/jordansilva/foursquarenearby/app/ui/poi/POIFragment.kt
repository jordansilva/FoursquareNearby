package com.jordansilva.foursquarenearby.app.ui.poi

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jordansilva.foursquarenearby.app.R
import com.jordansilva.foursquarenearby.app.model.POIView
import com.jordansilva.foursquarenearby.app.ui.BaseFragment
import com.jordansilva.foursquarenearby.app.util.OnItemClickViewListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.jetbrains.anko.startActivity

class POIFragment : BaseFragment(), OnItemClickViewListener<POIView> {

    private val viewModel by viewModel<POIViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_poi_list, container, false)
        viewModel.listPlaces.observeForever { loadData(view, it) }

        return view
    }

    private fun loadData(view: View, items: List<POIView>) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = POIRecyclerViewAdapter(items, this)
    }

    override fun onClickItem(view: View, item: POIView) {
        val intent = Intent(activity, POIDetailActivity::class.java)
        intent.putExtra(POIDetailActivity.ARGS_POI_ID, item.id)
        startActivity(intent)
    }


    companion object {
        @JvmStatic
        fun newInstance() = POIFragment()
    }
}
