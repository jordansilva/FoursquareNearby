package com.jordansilva.foursquarenearby.app.ui.poi

import androidx.lifecycle.MutableLiveData
import com.jordansilva.foursquarenearby.app.mapper.MapperView
import com.jordansilva.foursquarenearby.app.model.POIView
import com.jordansilva.foursquarenearby.app.ui.BaseViewModel
import com.jordansilva.foursquarenearby.domain.interactor.poi.GetNearbyPOIsUseCase
import com.jordansilva.foursquarenearby.domain.interactor.poi.GetPOIPhotosUseCase
import com.jordansilva.foursquarenearby.domain.interactor.poi.GetPOIUseCase
import com.jordansilva.foursquarenearby.domain.model.POI
import com.jordansilva.foursquarenearby.infrastructure.util.extensions.isNotNullOrEmpty

class POIViewModel(private val getPOIUseCase: GetPOIUseCase,
                   private val getNearbyPOIsUseCase: GetNearbyPOIsUseCase,
                   private val getPOIPhotosUseCase: GetPOIPhotosUseCase,
                   private val mapper: MapperView<POI, POIView>) : BaseViewModel() {

    var query: String? = null
    val listPlaces: MutableLiveData<List<POIView>> = MutableLiveData()
    val place: MutableLiveData<POIView> = MutableLiveData()
    val photos: MutableLiveData<List<String>> = MutableLiveData()
    val loading: MutableLiveData<Boolean> = MutableLiveData()


    fun getPOI(id: String, forceRefresh: Boolean = false) {
        if (!forceRefresh && place.value?.id.equals(id)) {
            place.postValue(place.value)
            return
        }

        loading.postValue(true)
        launchAsync {
            try {
                val item = getPOIUseCase.execute(id)
                place.postValue(mapper.mapFromDomain(item))

                val itemPhotos = getPOIPhotosUseCase.execute(id)
                photos.postValue(itemPhotos)
            } catch (ex: Exception) {
                ex.printStackTrace()
            } finally {
                loading.postValue(false)
            }
        }
    }

    fun getNearbyPOIs(location: String, radius: Int = 1000, limit: Int = 10, forceRefresh: Boolean = false) {

        val newQuery = location.trim()

        if (newQuery.isEmpty()) {
            listPlaces.postValue(arrayListOf())
            return
        }

        if (!forceRefresh && newQuery.equals(query, true) && listPlaces.value.isNotNullOrEmpty()) {
            listPlaces.postValue(listPlaces.value)
            return
        }

        loading.postValue(true)
        query = newQuery
        val request = GetNearbyPOIsUseCase.NearbyPOIRequest(newQuery, radius, limit)

        launchAsync {
            try {
                val list = getNearbyPOIsUseCase.execute(request)
                listPlaces.postValue(list.map { mapper.mapFromDomain(it) })
            } catch (ex: Exception) {
                ex.printStackTrace()
            } finally {
                loading.postValue(false)
            }
        }
    }

}