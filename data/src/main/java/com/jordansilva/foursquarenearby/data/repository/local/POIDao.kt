package com.jordansilva.foursquarenearby.data.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jordansilva.foursquarenearby.domain.model.POI

@Dao
interface POIDao : BaseDao<POI> {

    @Query("SELECT * FROM pois WHERE queryString = :query ORDER BY name")
    fun listPOIs(query: String) : List<POI>

    @Query("DELETE FROM pois WHERE queryString = :query AND updated <= strftime('%s', date('now', '-1 day'))")
    fun deletePOIs(query: String)

    @Query("SELECT * FROM pois ORDER BY name")
    fun listPOIs() : List<POI>

    @Query("SELECT * FROM pois WHERE id = :id ")
    fun getById(id: String): POI

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun savePOIs(obj: List<POI>)
}