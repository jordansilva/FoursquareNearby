package com.jordansilva.foursquarenearby.app.model

data class POIView(var id: String,
                   var name: String,
                   var description: String?,
                   var location: String,
                   var category: String?,
                   var categories: ArrayList<String>,
                   var bestPhoto: String?) {

    var photos: ArrayList<String> = arrayListOf()
    var contact: String? = null
    var facebookUsername: String? = null
    var twitter: String? = null
    var rating: Double? = null
}