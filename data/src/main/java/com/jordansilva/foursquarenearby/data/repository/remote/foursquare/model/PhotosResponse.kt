package com.jordansilva.foursquarenearby.data.repository.remote.foursquare.model

import com.google.gson.annotations.SerializedName

data class PhotosResponse(@SerializedName("groups") val groups: ArrayList<PhotoGroup>) {

    data class PhotoGroup(@SerializedName("type") val type: String,
                          @SerializedName("name") val name: String,
                          @SerializedName("items") val items: List<Photo>)

    data class Photo(@SerializedName("prefix") val prefix: String,
                     @SerializedName("suffix") val suffix: String,
                     @SerializedName("width") val width: Int,
                     @SerializedName("height") val height: Int)


}
