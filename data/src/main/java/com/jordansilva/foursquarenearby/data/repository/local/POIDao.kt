package com.jordansilva.foursquarenearby.data.repository.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import com.jordansilva.foursquarenearby.domain.model.POI

@Dao
interface POIDao : BaseDao<POI> {

    @Query("SELECT * FROM pois WHERE queryString = :query ORDER BY name")
    fun listPOIs(query: String) : LiveData<List<POI>>

    @Query("DELETE FROM pois WHERE queryString = :query")
    fun deletePOIs(query: String)

    @Query("SELECT * FROM pois ORDER BY name")
    fun listPOIs() : LiveData<List<POI>>

    @Query("SELECT * FROM pois WHERE id = :id ")
    fun getById(id: String): LiveData<POI>
}