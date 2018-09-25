package com.jordansilva.foursquarenearby.data.repository.remote.foursquare.model

import com.google.gson.annotations.SerializedName

data class VenueDetailsResponse(@SerializedName("venue") val result : VenueResponse?)