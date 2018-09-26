package com.jordansilva.foursquarenearby.data.repository

import com.jordansilva.foursquarenearby.data.repository.local.POIDao
import com.jordansilva.foursquarenearby.data.repository.remote.foursquare.VenuesApi
import com.jordansilva.foursquarenearby.domain.model.POI
import com.jordansilva.foursquarenearby.domain.repository.POIRepository

class POIDataRepository constructor(private val apiVenues: VenuesApi,
                                    private val poiDao: POIDao) : POIRepository {


    override suspend fun getNearbyPOIs(location: String, radius: Int, limit: Int): List<POI> {
        try {
            //TODO: Implement 
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return arrayListOf()
    }

    override suspend fun getNearbyPOIs(latitude: Double, longitude: Double, radius: Int, limit: Int): List<POI> {
        try {
            //TODO: Implement 
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return arrayListOf()
    }

    override suspend fun getPOI(id: String): POI {
        try {
            //TODO: Implement 
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return POI("aa", "aa", null, "aa", "aa", 0.0)
    }
}