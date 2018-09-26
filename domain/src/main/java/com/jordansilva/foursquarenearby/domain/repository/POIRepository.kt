package com.jordansilva.foursquarenearby.domain.repository

import com.jordansilva.foursquarenearby.domain.model.POI

interface POIRepository {
    suspend fun getNearbyPOIs(location: String, radius: Int, limit: Int): List<POI>
    suspend fun getNearbyPOIs(latitude: Double, longitude: Double, radius: Int, limit: Int): List<POI>

    suspend fun getPOI(id: String): POI
}