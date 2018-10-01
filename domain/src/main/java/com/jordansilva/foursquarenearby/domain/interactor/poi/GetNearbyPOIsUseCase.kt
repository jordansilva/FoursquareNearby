package com.jordansilva.foursquarenearby.domain.interactor.poi

import androidx.lifecycle.LiveData
import com.jordansilva.foursquarenearby.domain.interactor.BaseUseCase
import com.jordansilva.foursquarenearby.domain.model.POI
import com.jordansilva.foursquarenearby.domain.repository.POIRepository

class GetNearbyPOIsUseCase(private var poiRepository: POIRepository) : BaseUseCase() {

    data class NearbyPOIRequest(val location: String,
                                val radius: Int,
                                val limit: Int)

    suspend fun execute(request: NearbyPOIRequest): List<POI> {
        try {
            return async { poiRepository.getNearbyPOIs(request.location, request.radius, request.limit) }.await()
        } catch (exception: Exception) {
            throw exception
        }
    }
}