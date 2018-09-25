package com.jordansilva.foursquarenearby.infrastructure.util.extensions

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager

fun Application.isInternetAvailable(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}