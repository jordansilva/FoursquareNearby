package com.jordansilva.foursquarenearby.data.repository.model

import androidx.room.Entity
import androidx.room.PrimaryKey

//TODO: Implement photos, contact, address
@Entity(tableName = "pois")
data class POI(@PrimaryKey var id: String,
               var name: String,
               var description: String?,
               var contact: String,
               var address: String,
               var rating: Double) : BaseModel()