package com.jordansilva.foursquarenearby.data.repository.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class HttpFoursquareInterceptor(private val clientId: String,
                                private val secret: String,
                                private val version: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response? {
        val original = chain.request()
        val request = newRequest(original)
        return chain.proceed(request)
    }

    private fun newRequest(original: Request): Request {
        val originalUrl = original.url()

        //Replacing Foursquare URL with API version and Client keys
        val newUrl = originalUrl.newBuilder()
                .addQueryParameter("client_id", clientId)
                .addQueryParameter("client_secret", secret)
                .addQueryParameter("v", version)
                .build()

        return original.newBuilder()
                .url(newUrl)
                .build()
    }

}