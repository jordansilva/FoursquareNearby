package com.jordansilva.foursquarenearby.app.ui.poi

import androidx.lifecycle.MutableLiveData
import com.jordansilva.foursquarenearby.app.mapper.MapperView
import com.jordansilva.foursquarenearby.app.model.POIView
import com.jordansilva.foursquarenearby.app.ui.BaseViewModel
import com.jordansilva.foursquarenearby.domain.interactor.poi.GetNearbyPOIsUseCase
import com.jordansilva.foursquarenearby.domain.interactor.poi.GetPOIUseCase
import com.jordansilva.foursquarenearby.domain.model.POI

class POIViewModel(private val getPOIUseCase: GetPOIUseCase,
                   private val getNearbyPOIsUseCase: GetNearbyPOIsUseCase,
                   private val mapper: MapperView<POI, POIView>) : BaseViewModel() {

    val listPlaces: MutableLiveData<List<POIView>> = MutableLiveData()
    val place: MutableLiveData<POIView> = MutableLiveData()

    init {
        getNearbyPOIs()
    }

    fun getPOI(id: String) {
        launchAsync {
            try {
                val item = getPOIUseCase.execute(id)
                val poiView = mapper.mapFromDomain(item)
                place.postValue(poiView)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    fun getNearbyPOIs() {
        //FIXME: Improve construction of data class
        val request = GetNearbyPOIsUseCase.NearbyPOIRequest("rotterdam", 1000, 50)

        launchAsync {
            try {
                val list = getNearbyPOIsUseCase.execute(request)
                val listPOIView = list.map { mapper.mapFromDomain(it) }
                listPlaces.postValue(listPOIView)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

}