package com.jordansilva.foursquarenearby.data.repository

import com.jordansilva.foursquarenearby.data.repository.exception.NetworkApiException
import com.jordansilva.foursquarenearby.data.repository.local.POIDao
import com.jordansilva.foursquarenearby.data.repository.mapper.POIMapper
import com.jordansilva.foursquarenearby.data.repository.remote.foursquare.VenuesApi
import com.jordansilva.foursquarenearby.domain.model.POI
import com.jordansilva.foursquarenearby.domain.repository.POIRepository
import com.jordansilva.foursquarenearby.infrastructure.util.extensions.isNotNullOrEmpty
import com.jordansilva.foursquarenearby.infrastructure.util.extensions.notNull
import java.util.*

class POIDataRepository constructor(private val apiVenues: VenuesApi,
                                    private val poiDao: POIDao) : POIRepository {


    override suspend fun getNearbyPOIs(location: String, radius: Int, limit: Int): List<POI> {

        val queryId = "$location,$radius"

        try {
            //Checking API
            val apiResult = apiVenues.searchByLocation(location, "browse", radius, limit).await()

            when (apiResult.meta?.code) {
                200 -> {
                    val apiListResult = apiResult.response?.result?.map {
                        POIMapper.mapToDomain(it).apply {
                            queryString = queryId
                            updated = Date()
                        }
                    }

                    //saving data
                    apiListResult.isNotNullOrEmpty().let {
                        poiDao.deletePOIs(queryId) //Delete old items
                        poiDao.savePOIs(apiListResult!!) //Don't replace items
                    }
                }
            }
        } catch (ex: NetworkApiException) {
            ex.printStackTrace()
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw ex
        }

        return poiDao.listPOIs(queryId)
    }

    override suspend fun getPOI(id: String): POI {

        try {
            //Checking API
            val apiResult = apiVenues.getById(id).await()
            when (apiResult.meta?.code) {
                200 -> {
                    val poi = apiResult.response?.result?.let { POIMapper.mapToDomain(it) }
                    poi.notNull {
                        val localResult = poiDao.getById(id)
                        it.queryString = localResult.queryString
                        it.updated = Date()
                        poiDao.update(it)
                    }
                }
            }
        } catch (ex: NetworkApiException) {
            ex.printStackTrace()
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw ex
        }

        return poiDao.getById(id)
    }
}