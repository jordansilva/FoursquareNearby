package com.jordansilva.foursquarenearby.data.repository.remote.foursquare.model

import com.google.gson.annotations.SerializedName

data class VenuesSearchResponse(@SerializedName("venues") val result : ArrayList<VenueResponse>?)