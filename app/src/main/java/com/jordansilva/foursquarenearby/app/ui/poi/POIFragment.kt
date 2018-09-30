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
import com.jordansilva.foursquarenearby.app.ui.poi.adapter.POIRecyclerViewAdapter
import com.jordansilva.foursquarenearby.app.util.OnItemClickViewListener
import com.jordansilva.foursquarenearby.infrastructure.util.extensions.drawableLeft
import kotlinx.android.synthetic.main.fragment_poi_list.view.*
import org.jetbrains.anko.sdk25.coroutines.textChangedListener
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class POIFragment : BaseFragment(), OnItemClickViewListener<POIView> {

    private val viewModel by sharedViewModel<POIViewModel>()
    private lateinit var mAdapter: POIRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_poi_list, container, false)

        configureUi(view)

        viewModel.listPlaces.observeForever { loadData(it) }
        fechData("rotterdam")

        return view
    }

    private fun configureUi(view: View) {
        mAdapter = POIRecyclerViewAdapter(null, this)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = mAdapter

        view.editSearch.drawableLeft = R.drawable.ic_search_black_24dp
        view.editSearch.textChangedListener {
            onTextChanged { charSequence, _, _, _ ->
                mAdapter.filter.filter(charSequence)
            }
        }
    }

    private fun fechData(location: String) {
        viewModel.getNearbyPOIs(location)
    }

    private fun loadData(items: List<POIView>) {
        mAdapter.updateData(items)
    }

    override fun onClickItem(view: View, item: POIView) {
        val intent = Intent(activity, POIDetailActivity::class.java)
        intent.putExtra(POIDetailActivity.ARGS_POI_ID, item.id)
        intent.putExtra(POIDetailActivity.ARGS_POI_NAME, item.name)
        intent.putExtra(POIDetailActivity.ARGS_POI_LOCATION, item.location)
        startActivity(intent)
    }


    companion object {
        @JvmStatic
        fun newInstance() = POIFragment()
    }
}
