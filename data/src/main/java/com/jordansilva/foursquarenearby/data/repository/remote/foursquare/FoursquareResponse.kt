package com.jordansilva.foursquarenearby.data.repository.remote.foursquare

class FoursquareResponse<T> {
    val meta : Meta? = null
    val response : T? = null

    data class Meta(var code: Int, var requestId: String)
}