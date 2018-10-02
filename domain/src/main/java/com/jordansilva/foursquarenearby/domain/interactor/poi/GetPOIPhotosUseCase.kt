package com.jordansilva.foursquarenearby.domain.interactor.poi

import com.jordansilva.foursquarenearby.domain.interactor.BaseUseCase
import com.jordansilva.foursquarenearby.domain.repository.POIRepository

class GetPOIPhotosUseCase(private var poiRepository: POIRepository) : BaseUseCase() {

    suspend fun execute(id: String): List<String> {
        try {
            if (id.isBlank()) throw Exception("Invalid id!")

            return async { poiRepository.getPOIPhotos(id) }.await()
        } catch (exception: Exception) {
            throw exception
        }
    }
}