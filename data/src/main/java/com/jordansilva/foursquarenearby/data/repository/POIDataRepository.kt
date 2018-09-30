package com.jordansilva.foursquarenearby.data.repository

import android.text.TextUtils
import com.jordansilva.foursquarenearby.data.repository.local.POIDao
import com.jordansilva.foursquarenearby.data.repository.mapper.POIMapper
import com.jordansilva.foursquarenearby.data.repository.remote.foursquare.VenuesApi
import com.jordansilva.foursquarenearby.domain.model.POI
import com.jordansilva.foursquarenearby.domain.repository.POIRepository

class POIDataRepository constructor(private val apiVenues: VenuesApi,
                                    private val poiDao: POIDao) : POIRepository {


    override suspend fun getNearbyPOIs(location: String, radius: Int, limit: Int): List<POI> {
        try {
            val result = apiVenues.searchByLocation(location, "browse", radius, limit).await()
            val listOfVenues = result.response?.result
            val listOfPOIs = listOfVenues?.map { POIMapper.mapToDomain(it) }

            return listOfPOIs ?: arrayListOf()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return arrayListOf()
    }

    override suspend fun getPOI(id: String): POI {
        try {
            val result = apiVenues.getById(id).await()
            val venue = result.response?.result

            return venue?.let { POIMapper.mapToDomain(it) } ?: throw Exception()
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw ex
        }
    }
}