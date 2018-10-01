package com.jordansilva.foursquarenearby.app.ui.poi

import androidx.lifecycle.MutableLiveData
import com.jordansilva.foursquarenearby.app.mapper.MapperView
import com.jordansilva.foursquarenearby.app.model.POIView
import com.jordansilva.foursquarenearby.app.ui.BaseViewModel
import com.jordansilva.foursquarenearby.domain.interactor.poi.GetNearbyPOIsUseCase
import com.jordansilva.foursquarenearby.domain.interactor.poi.GetPOIUseCase
import com.jordansilva.foursquarenearby.domain.model.POI
import com.jordansilva.foursquarenearby.infrastructure.util.extensions.isNotNullOrEmpty

class POIViewModel(private val getPOIUseCase: GetPOIUseCase,
                   private val getNearbyPOIsUseCase: GetNearbyPOIsUseCase,
                   private val mapper: MapperView<POI, POIView>) : BaseViewModel() {

    private var mQuery: String? = null
    val listPlaces: MutableLiveData<List<POIView>> = MutableLiveData()
    val place: MutableLiveData<POIView> = MutableLiveData()

    fun getPOI(id: String) {
        if (place.value?.id.equals(id)) {
            place.postValue(place.value)
            return
        }

        launchAsync {
            try {
                val item = getPOIUseCase.execute(id)
                place.postValue(mapper.mapFromDomain(item))
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    fun getNearbyPOIs(location: String, radius: Int = 1000, limit: Int = 10) {
        if (location.equals(mQuery, true) && listPlaces.value.isNotNullOrEmpty()) {
            listPlaces.postValue(listPlaces.value)
            return
        }

        mQuery = location
        val request = GetNearbyPOIsUseCase.NearbyPOIRequest(location, radius, limit)

        launchAsync {
            try {
                val list = getNearbyPOIsUseCase.execute(request)
                listPlaces.postValue(list.map { mapper.mapFromDomain(it) })
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

}