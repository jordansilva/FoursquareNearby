package com.jordansilva.foursquarenearby.domain.interactor.poi

import com.jordansilva.foursquarenearby.domain.interactor.BaseUseCase
import com.jordansilva.foursquarenearby.domain.model.POI
import com.jordansilva.foursquarenearby.domain.repository.POIRepository

class GetNearbyPOIsUseCase(private var poiRepository: POIRepository) : BaseUseCase() {

    data class NearbyPOIRequest(val location: String?,
                                val latitude: Double?,
                                val longitude: Double?,
                                val radius: Int,
                                val limit: Int) {

        fun hasCoordinates(): Boolean {
            return latitude != null && longitude != null
                    && !latitude.isNaN() && !longitude.isNaN()
        }
    }

    suspend fun execute(request: NearbyPOIRequest): List<POI> {
        try {
            if (request.location.isNullOrBlank() && !request.hasCoordinates())
                throw Exception("The location is missing!")

            val list: List<POI>
            if (request.hasCoordinates()) {
                list = async {
                    poiRepository.getNearbyPOIs(request.latitude!!, request.longitude!!,
                            request.radius, request.limit)
                }.await()
            } else {
                list = async { poiRepository.getNearbyPOIs(request.location!!, request.radius, request.limit) }.await()
            }

            return list
        } catch (exception: Exception) {
            throw exception
        }
    }
}