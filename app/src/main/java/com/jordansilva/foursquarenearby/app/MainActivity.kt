package com.jordansilva.foursquarenearby.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jordansilva.foursquarenearby.app.ui.home.HomeActivity
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity<HomeActivity>()
        //loadFoursquareData()
    }

}
