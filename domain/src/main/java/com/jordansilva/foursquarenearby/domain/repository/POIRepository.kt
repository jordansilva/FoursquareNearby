package com.jordansilva.foursquarenearby.domain.repository

import androidx.lifecycle.LiveData
import com.jordansilva.foursquarenearby.domain.model.POI

interface POIRepository {
    suspend fun getNearbyPOIs(location: String, radius: Int, limit: Int): List<POI>
    suspend fun getPOI(id: String): POI
    suspend fun getPOIPhotos(id: String): List<String>
}