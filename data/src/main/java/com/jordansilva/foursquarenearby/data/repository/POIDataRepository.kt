package com.jordansilva.foursquarenearby.data.repository

import com.jordansilva.foursquarenearby.data.repository.exception.NetworkApiException
import com.jordansilva.foursquarenearby.data.repository.local.POIDao
import com.jordansilva.foursquarenearby.data.repository.mapper.POIMapper
import com.jordansilva.foursquarenearby.data.repository.remote.foursquare.VenuesApi
import com.jordansilva.foursquarenearby.domain.model.POI
import com.jordansilva.foursquarenearby.domain.repository.POIRepository
import com.jordansilva.foursquarenearby.infrastructure.util.extensions.notNull
import com.jordansilva.foursquarenearby.infrastructure.util.extensions.notNullOrEmpty
import retrofit2.HttpException
import java.util.*

class POIDataRepository constructor(private val apiVenues: VenuesApi,
                                    private val poiDao: POIDao) : POIRepository {


    override suspend fun getNearbyPOIs(location: String, radius: Int, limit: Int): List<POI> {

        val query = location.trim().toLowerCase()
        val queryId = "$query,$radius"

        try {
            //Delete POIs cached older than 1 day
            poiDao.deletePOIs(queryId)

            //Checking API
            val apiResult = apiVenues.searchByLocation(query, "browse", radius, limit).await()

            when (apiResult.meta?.code) {
                200 -> {
                    val apiListResult = apiResult.response?.result?.map {
                        POIMapper.mapToDomain(it).apply {
                            queryString = queryId
                            updated = Date()
                        }
                    }
                    //Saving data... Don't replace items.
                    //I might have more data saved in POI when getting an unique POI by getPOI
                    //Thus, I'm not replacing POIs when listing POIs
                    apiListResult.notNullOrEmpty { poiDao.savePOIs(apiListResult!!)  }
                }
            }
        } catch (ex: NetworkApiException) {
            ex.printStackTrace()
        } catch (ex: HttpException) {
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
        } catch (ex: HttpException) {
            ex.printStackTrace()
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw ex
        }

        return poiDao.getById(id)
    }
}