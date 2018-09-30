package com.jordansilva.foursquarenearby.app.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.jordansilva.foursquarenearby.app.R
import com.jordansilva.foursquarenearby.app.ui.BaseActivity
import com.jordansilva.foursquarenearby.app.ui.poi.POIFragment
import com.jordansilva.foursquarenearby.app.ui.poi.POIViewModel
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        configureUi()
        init()
    }

    private fun configureUi() {
        configureToolbar(false)
    }

    fun init() {
        showFragment(POIFragment.newInstance())
    }

    private fun showFragment(fragment: Fragment): Boolean {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.contentHome, fragment)
        transaction.commitAllowingStateLoss()

        return true
    }

}
