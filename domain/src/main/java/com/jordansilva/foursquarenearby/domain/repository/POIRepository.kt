package com.jordansilva.foursquarenearby.domain.repository

import androidx.lifecycle.LiveData
import com.jordansilva.foursquarenearby.domain.model.POI

interface POIRepository {
    suspend fun getNearbyPOIs(location: String, radius: Int, limit: Int): LiveData<List<POI>>

    suspend fun getPOI(id: String): LiveData<POI>
}