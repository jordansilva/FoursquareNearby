package com.jordansilva.foursquarenearby.data.repository.local

import androidx.room.Dao
import androidx.room.Query
import com.jordansilva.foursquarenearby.data.repository.model.POI

@Dao
interface POIDao : BaseDao<POI> {

    @Query("SELECT * FROM pois ORDER BY name")
    fun listPOIs() : List<POI>
}