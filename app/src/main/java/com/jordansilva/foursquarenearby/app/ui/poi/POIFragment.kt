package com.jordansilva.foursquarenearby.app.ui.poi

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jordansilva.foursquarenearby.app.R
import com.jordansilva.foursquarenearby.app.model.POIView
import com.jordansilva.foursquarenearby.app.ui.BaseFragment
import com.jordansilva.foursquarenearby.app.ui.poi.adapter.POIAdapter
import com.jordansilva.foursquarenearby.app.util.OnItemClickViewListener
import com.jordansilva.foursquarenearby.infrastructure.util.extensions.drawableLeft
import com.jordansilva.foursquarenearby.infrastructure.util.extensions.hideKeyboard
import com.jordansilva.foursquarenearby.infrastructure.util.extensions.isNotNullOrEmpty
import com.jordansilva.foursquarenearby.infrastructure.util.extensions.notNull
import kotlinx.android.synthetic.main.fragment_poi_list.*
import kotlinx.android.synthetic.main.fragment_poi_list.view.*
import org.jetbrains.anko.sdk25.coroutines.onEditorAction
import org.jetbrains.anko.sdk25.coroutines.textChangedListener
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class POIFragment : BaseFragment(), OnItemClickViewListener<POIView> {

    private val viewModel by sharedViewModel<POIViewModel>()
    private lateinit var mAdapter: POIAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_poi_list, container, false)

        configureUi(view)
        viewModel.listPlaces.observe(this, Observer { loadData(view, it) })
        viewModel.loading.observeForever { view.refreshLayout.isRefreshing = it }
        fetchData("Rotterdam")

        return view
    }

    private fun configureUi(view: View) {
        mAdapter = POIAdapter(null, this)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = mAdapter

        view.editSearch.drawableLeft = R.drawable.ic_search_black_24dp
        view.editSearch.onEditorAction { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    view.editSearch.hideKeyboard()
                    Handler().postDelayed({ fetchData(editSearch.text.toString()) }, 50)

                }
            }
        }

        view.editSearch.textChangedListener {
            onTextChanged { charSequence, _, _, _ ->
                //Filter inside the RecyclerView
                //mAdapter.filter.filter(charSequence)
            }
        }

        view.refreshLayout.setOnRefreshListener { viewModel.query.notNull { fetchData(it, true) } }
    }

    private fun fetchData(location: String, forceRefresh: Boolean = false) {
        viewModel.getNearbyPOIs(location, forceRefresh = forceRefresh)
    }

    private fun loadData(view: View, items: List<POIView>) {
        view.refreshLayout.isRefreshing = false
        view.resultsView.isVisible = items.isNotNullOrEmpty()
        view.emptyView.isVisible = items.isEmpty()
        view.textResults.text = viewModel.query

        view.recyclerView.scheduleLayoutAnimation()
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
