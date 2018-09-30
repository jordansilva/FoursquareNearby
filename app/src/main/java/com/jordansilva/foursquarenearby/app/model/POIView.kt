package com.jordansilva.foursquarenearby.app.model

data class POIView(var id: String,
                   var name: String,
                   var description: String?,
                   var location: String,
                   var category: String?,
                   var categories: ArrayList<String>,
                   var photo: String?)