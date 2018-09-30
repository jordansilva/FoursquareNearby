package com.jordansilva.foursquarenearby.app.ui.poi

import android.os.Bundle
import android.util.Log
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.jordansilva.foursquarenearby.app.R
import com.jordansilva.foursquarenearby.app.model.POIView
import com.jordansilva.foursquarenearby.app.ui.BaseActivity
import com.jordansilva.foursquarenearby.app.ui.poi.adapter.SliderImageAdapter
import com.jordansilva.foursquarenearby.infrastructure.util.extensions.isNull
import com.jordansilva.foursquarenearby.infrastructure.util.extensions.px
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.IIcon
import com.mikepenz.iconics.view.IconicsImageView
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_poi_detail.*
import kotlinx.android.synthetic.main.content_poidetail.*
import kotlinx.android.synthetic.main.row_poi_detail_property.view.*
import org.jetbrains.anko.browse
import org.jetbrains.anko.ctx
import org.jetbrains.anko.makeCall
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.koin.androidx.viewmodel.ext.android.viewModel


class POIDetailActivity : BaseActivity() {

    private val viewModel by viewModel<POIViewModel>()
    private lateinit var mItemId: String

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
    }

    private fun init() {
        mItemId = intent.getStringExtra(ARGS_POI_ID)

        val itemName = intent.getStringExtra(ARGS_POI_NAME)
        itemName?.let { toolbarLayout.title = it }

        val itemLocation = intent.getStringExtra(ARGS_POI_LOCATION)
        viewLocation.textProperty.text = itemLocation

        viewLocation.imageProperty.icon(MaterialDesignIconic.Icon.gmi_pin, android.R.color.holo_red_light)
        viewContact.imageProperty.icon(MaterialDesignIconic.Icon.gmi_phone, R.color.dark_gray)
        viewFacebook.imageProperty.icon(MaterialDesignIconic.Icon.gmi_facebook, R.color.dark_gray)
        viewTwitter.imageProperty.icon(MaterialDesignIconic.Icon.gmi_twitter, R.color.dark_gray)

        fetchPOIbyId(mItemId)
    }

    private fun fetchPOIbyId(id: String) {
        viewModel.getPOI(id)
    }

    private fun loadPOI(place: POIView) {
        toolbarLayout.title = place.name

        viewLocation.textProperty.text = place.location

        viewContact.isVisible = !place.contact.isNullOrEmpty()
        viewContact.textProperty.text = place.contact
        viewContact.onClick { makeCall(place.contact!!) }

        viewFacebook.isVisible = !place.facebookUsername.isNullOrEmpty()
        viewFacebook.textProperty.text = place.facebookUsername
        viewFacebook.onClick { browse("https://facebook.com/${place.facebookUsername}") }

        viewTwitter.isVisible = !place.twitter.isNullOrEmpty()
        viewTwitter.textProperty.text = "@${place.twitter}"
        viewTwitter.onClick { browse("https://twitter.com/${place.twitter}") }

        linearDescription.isVisible = !place.description.isNullOrEmpty()
        textDescription.text = place.description

        viewPager.pageMargin = 16.px
        viewPager.adapter = SliderImageAdapter(ctx, place.photos)
        tabDots.setupWithViewPager(viewPager, true)
    }

    fun IconicsImageView.icon(icon: IIcon, @ColorRes colorId: Int, size: Int = 16) {
        this.icon = IconicsDrawable(ctx)
                .icon(icon)
                .color(ContextCompat.getColor(ctx, colorId))
                .sizeDp(size)
    }
}
