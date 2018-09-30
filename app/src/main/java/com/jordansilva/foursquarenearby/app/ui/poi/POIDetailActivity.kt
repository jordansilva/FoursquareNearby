package com.jordansilva.foursquarenearby.app.ui.poi

import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.internal.CollapsingTextHelper
import com.google.android.material.snackbar.Snackbar
import com.jordansilva.foursquarenearby.app.R
import com.jordansilva.foursquarenearby.app.model.POIView
import com.jordansilva.foursquarenearby.app.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_poi_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class POIDetailActivity : BaseActivity() {

    private val viewModel by viewModel<POIViewModel>()

    companion object {
        const val ARGS_POI_ID = "POI_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poi_detail)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        viewModel.place.observe(this, Observer { loadPOI(it) })
        val poiId = intent.getStringExtra(ARGS_POI_ID)
        fetchPOIbyId(poiId)
    }

    private fun loadPOI(place: POIView) {
        toolbarLayout.title = place.name
        Log.d("PLACE", place.toString())
    }

    private fun fetchPOIbyId(id: String) {
        viewModel.getPOI(id)
    }
}
