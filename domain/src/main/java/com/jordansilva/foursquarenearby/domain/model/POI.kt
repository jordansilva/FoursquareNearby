package com.jordansilva.foursquarenearby.domain.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.lang.StringBuilder
import java.util.*

//TODO: Implement photos, contact
@Entity(tableName = "pois")
data class POI(@PrimaryKey var id: String,
               var name: String,
               var description: String?,
               var contact: String?,
               var rating: Double) : BaseModel() {


    //Address info
    @Embedded
    var address: Address? = null
    var category: String? = null
    var categories: ArrayList<String> = arrayListOf()
    var twitter: String? = null
    var facebookUsername: String? = null
    var photos: ArrayList<String> = arrayListOf()
    var bestPhoto: String? = null
    var queryString: String? = null
    var updated: Date? = null

    data class Address(var address: String?,
                       var lat: Double?,
                       var lng: Double?,
                       var city: String?,
                       var state: String?,
                       var cc: String?,
                       var country: String?) {

        override fun toString(): String {
            val formattedAddress = StringBuilder()
            if (!address.isNullOrEmpty()) formattedAddress.append("$address, ")
            if (!city.isNullOrEmpty()) formattedAddress.append("$city, ")
            if (!cc.isNullOrEmpty()) {
                if (formattedAddress.isEmpty() && !country.isNullOrEmpty())
                    formattedAddress.append(country)
                else
                    formattedAddress.append(cc)
            }

            return formattedAddress.toString()
        }
    }

}