package com.jordansilva.foursquarenearby.app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.jordansilva.foursquarenearby.app.ui.home.HomeActivity
import com.jordansilva.foursquarenearby.data.repository.POIDataRepository
import com.jordansilva.foursquarenearby.data.repository.local.AppDatabase
import com.jordansilva.foursquarenearby.data.repository.remote.foursquare.FoursquareServiceFactory
import com.jordansilva.foursquarenearby.data.repository.remote.interceptor.HttpFoursquareInterceptor
import com.jordansilva.foursquarenearby.data.repository.remote.interceptor.NetworkConnectionInterceptor
import com.jordansilva.foursquarenearby.domain.interactor.poi.GetNearbyPOIsUseCase
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity<HomeActivity>()
        //loadFoursquareData()
    }

    private fun loadFoursquareData() {
        Log.d("list", "LOAD FOURSQUARE DATA")
        var httpFoursquareInterceptor = HttpFoursquareInterceptor()
        var networkConnectionInterceptor = NetworkConnectionInterceptor(application)

        var venuesApi = FoursquareServiceFactory.makeVenuesApiService(httpFoursquareInterceptor,
                networkConnectionInterceptor)

        var poiDao = AppDatabase.getInstance(this).poiDao()
        var repository = POIDataRepository(venuesApi, poiDao)

        var useCase = GetNearbyPOIsUseCase(repository)
        var request = GetNearbyPOIsUseCase.NearbyPOIRequest("rotterdam", 1000, 50)

        val job: Job = launch(UI) {
            val list = useCase.execute(request)
            Log.d("list", "LIST")
            list.forEach { Log.d("LIST", it.toString()) }
        }
        job.invokeOnCompletion { }
    }
}
