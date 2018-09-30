package com.jordansilva.foursquarenearby.data.repository.remote.interceptor

import com.jordansilva.foursquarenearby.infrastructure.util.Constants
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class HttpFoursquareInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response? {
        val original = chain.request()
        val request = newRequest(original)
        return chain.proceed(request)
    }

    private fun newRequest(original: Request): Request {
        val originalUrl = original.url()

        //Replacing Foursquare URL with API version and Client keys
        val newUrl = originalUrl.newBuilder()
                .addQueryParameter("client_id", Constants.API.FOURSQUARE_CLIENT_ID)
                .addQueryParameter("client_secret", Constants.API.FOURSQUARE_CLIENT_SECRET)
                .addQueryParameter("v", Constants.API.FOURSQUARE_VERSION)
                .build()

        return original.newBuilder()
                .url(newUrl)
                .build()
    }

}