package com.jordansilva.foursquarenearby.data.repository.remote.foursquare.model

import com.google.gson.annotations.SerializedName

//TODO: Add photos
data class VenueResponse(@SerializedName("id") val id: String,
                         @SerializedName("name") val name: String,
                         @SerializedName("description") val description: String?,
                         @SerializedName("contact") val contact: Contact?,
                         @SerializedName("location") val location: Address,
                         @SerializedName("categories") val categories: Array<Category>,
                         @SerializedName("rating") val rating: Double,
                         @SerializedName("photos") val photos: PhotosResponse?,
                         @SerializedName("bestPhoto") val bestPhoto: PhotosResponse.Photo?) {

    data class Category(@SerializedName("name") val name: String,
                        @SerializedName("pluralName") val pluralName: String,
                        @SerializedName("shortName") val shortName: String,
                        @SerializedName("primary") val primary: Boolean)

    data class Contact(@SerializedName("phone") val phone: String?,
                       @SerializedName("formattedPhone") val formattedPhone: String?,
                       @SerializedName("twitter") val twitter: String?,
                       @SerializedName("facebook") val facebook: String?,
                       @SerializedName("facebookUsername") val facebookUsername: String?)

    data class Address(@SerializedName("address") val address: String?,
                       @SerializedName("crossStreet") val crossStreet: String?,
                       @SerializedName("lat") val lat: Double?,
                       @SerializedName("lng") val lng: Double?,
                       @SerializedName("distance") val distance: Int?,
                       @SerializedName("postalCode") val postalCode: String?,
                       @SerializedName("cc") val cc: String?,
                       @SerializedName("city") val city: String?,
                       @SerializedName("state") val state: String?,
                       @SerializedName("country") val country: String?,
                       @SerializedName("formattedAddress") val formattedAddress: Array<String>?)
}