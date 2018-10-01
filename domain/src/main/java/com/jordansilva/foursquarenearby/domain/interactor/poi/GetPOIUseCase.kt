package com.jordansilva.foursquarenearby.domain.interactor.poi

import androidx.lifecycle.LiveData
import com.jordansilva.foursquarenearby.domain.interactor.BaseUseCase
import com.jordansilva.foursquarenearby.domain.model.POI
import com.jordansilva.foursquarenearby.domain.repository.POIRepository

class GetPOIUseCase(private var poiRepository: POIRepository) : BaseUseCase() {

    suspend fun execute(id: String): POI {
        try {
            if (id.isBlank()) throw Exception("Invalid id!")

            return async { poiRepository.getPOI(id) }.await()
        } catch (exception: Exception) {
            throw exception
        }
    }
}