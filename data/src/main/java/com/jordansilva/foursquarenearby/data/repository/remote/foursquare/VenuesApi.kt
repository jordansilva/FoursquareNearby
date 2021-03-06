package com.jordansilva.foursquarenearby.data.repository.remote.foursquare

import com.jordansilva.foursquarenearby.data.repository.remote.foursquare.model.PhotosResponse
import com.jordansilva.foursquarenearby.data.repository.remote.foursquare.model.VenueDetailsResponse
import com.jordansilva.foursquarenearby.data.repository.remote.foursquare.model.VenueResponse
import com.jordansilva.foursquarenearby.data.repository.remote.foursquare.model.VenuesSearchResponse
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface VenuesApi {

    @GET("venues/search")
    fun searchByCoordinates(@Query ("ll") coordinates : String,
               @Query("intent") intent : String,
               @Query ("radius") radius: Int = 250, //default in meters
               @Query("limit") limit : Int) : Deferred<FoursquareResponse<VenuesSearchResponse>>

    @GET("venues/search")
    fun searchByLocation(@Query ("near") location: String,
               @Query("intent") intent : String,
               @Query ("radius") radius: Int = 250, //default in meters
               @Query("limit") limit : Int) : Deferred<FoursquareResponse<VenuesSearchResponse>>

    @GET("venues/{id}")
    fun getById(@Path ("id") id: String) : Deferred<FoursquareResponse<VenueDetailsResponse>>

    @GET("venues/{id}/photos")
    fun getPhotos(@Path ("id") id: String) : Deferred<FoursquareResponse<PhotosResponse>>
}