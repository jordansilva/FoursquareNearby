package com.jordansilva.foursquarenearby.data.repository.remote.interceptor

import android.app.Application
import com.jordansilva.foursquarenearby.data.repository.exception.NetworkApiException
import com.jordansilva.foursquarenearby.infrastructure.util.extensions.isInternetAvailable
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class NetworkConnectionInterceptor(private var app : Application) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!app.isInternetAvailable()) {
            throw NetworkApiException()
        }

        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }

}
