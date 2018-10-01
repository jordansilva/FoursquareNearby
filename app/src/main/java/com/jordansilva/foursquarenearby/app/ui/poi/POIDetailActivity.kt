package com.jordansilva.foursquarenearby.app.ui.poi

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.HORIZONTAL
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import com.jordansilva.foursquarenearby.app.R
import com.jordansilva.foursquarenearby.app.model.POIView
import com.jordansilva.foursquarenearby.app.ui.BaseActivity
import com.jordansilva.foursquarenearby.app.ui.poi.adapter.POIPropertyAdapter
import com.jordansilva.foursquarenearby.app.ui.poi.adapter.SliderImageAdapter
import com.jordansilva.foursquarenearby.app.util.OnItemClickViewListener
import com.jordansilva.foursquarenearby.infrastructure.util.extensions.notNull
import com.jordansilva.foursquarenearby.infrastructure.util.extensions.notNullOrEmpty
import com.jordansilva.foursquarenearby.infrastructure.util.extensions.px
import com.jordansilva.foursquarenearby.infrastructure.util.extensions.whenNotNull
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.IIcon
import com.mikepenz.iconics.view.IconicsImageView
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import kotlinx.android.synthetic.main.activity_poi_detail.*
import kotlinx.android.synthetic.main.content_poidetail.*
import kotlinx.android.synthetic.main.row_poi_detail_property.view.*
import org.jetbrains.anko.browse
import org.jetbrains.anko.ctx
import org.jetbrains.anko.makeCall
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.koin.androidx.viewmodel.ext.android.viewModel


class POIDetailActivity : BaseActivity(), OnItemClickViewListener<POIPropertyAdapter.POIProperty> {

    private val viewModel by viewModel<POIViewModel>()
    private lateinit var mItemId: String
    private lateinit var mAdapter: POIPropertyAdapter

    companion object {
        const val ARGS_POI_ID = "POI_ID"
        const val ARGS_POI_NAME = "POI_NAME"
        const val ARGS_POI_LOCATION = "POI_LOCATION"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poi_detail)

        viewModel.place.observe(this, Observer { loadPOI(it) })

        configureUi()
        init()
    }

    private fun configureUi() {
        configureToolbar()

        mAdapter = POIPropertyAdapter(null, this)
        recyclerProperties.adapter = mAdapter

        viewPager.pageMargin = 16.px
        tabDots.setupWithViewPager(viewPager, true)
    }

    private fun init() {
        mItemId = intent.getStringExtra(ARGS_POI_ID)

        val itemName = intent.getStringExtra(ARGS_POI_NAME)
        itemName?.let { toolbarLayout.title = it }

        val list = ArrayList<POIPropertyAdapter.POIProperty>()

        val itemLocation = intent.getStringExtra(ARGS_POI_LOCATION)
        list.add(POIPropertyAdapter.POIProperty(itemLocation, "LOCATION", MaterialDesignIconic.Icon.gmi_pin))

        mAdapter.updateData(list)

        fetchPOIbyId(mItemId)
    }

    private fun fetchPOIbyId(id: String) {
        viewModel.getPOI(id)
    }

    private fun loadPOI(place: POIView) {
        toolbarLayout.title = place.name

        val list = ArrayList<POIPropertyAdapter.POIProperty>()
        place.location.notNullOrEmpty { list.add(POIPropertyAdapter.POIProperty(it, "LOCATION", MaterialDesignIconic.Icon.gmi_pin)) }
        place.contact.notNullOrEmpty { list.add(POIPropertyAdapter.POIProperty(it, "CONTACT", MaterialDesignIconic.Icon.gmi_phone)) }
        place.facebookUsername.notNullOrEmpty { list.add(POIPropertyAdapter.POIProperty(it, "FACEBOOK", MaterialDesignIconic.Icon.gmi_facebook)) }
        place.twitter.notNullOrEmpty { list.add(POIPropertyAdapter.POIProperty(it, "TWITTER", MaterialDesignIconic.Icon.gmi_twitter)) }
        mAdapter.updateData(list)

        linearDescription.isVisible = !place.description.isNullOrEmpty()
        textDescription.text = place.description

        viewPager.adapter = SliderImageAdapter(ctx, place.photos)
    }

    override fun onClickItem(view: View, item: POIPropertyAdapter.POIProperty) {
        when (item.type) {
            "LOCATION" -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=${item.name}"))
                intent.`package` = "com.google.android.apps.maps"
                startActivity(intent)
            }
            "CONTACT" -> startActivity(Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", item.name, null)))
            "FACEBOOK" -> browse("https://facebook.com/${item.name}")
            "TWITTER" -> browse("https://twitter.com/${item.name}")
        }
    }


}
