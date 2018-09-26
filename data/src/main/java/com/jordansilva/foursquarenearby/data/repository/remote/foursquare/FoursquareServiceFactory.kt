package com.jordansilva.foursquarenearby.data.repository.remote.foursquare

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.jordansilva.foursquarenearby.data.repository.remote.ServiceFactory
import com.jordansilva.foursquarenearby.data.repository.remote.interceptor.HttpFoursquareInterceptor
import com.jordansilva.foursquarenearby.data.repository.remote.interceptor.NetworkConnectionInterceptor
import com.jordansilva.foursquarenearby.infrastructure.util.Constants
import com.jordansilva.foursquarenearby.infrastructure.util.factory.GsonFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FoursquareServiceFactory : ServiceFactory() {

    fun makeVenuesApiService(httpAuthInterceptor: HttpFoursquareInterceptor,
                             networkInterceptor: NetworkConnectionInterceptor): VenuesApi {
        return makeService(httpAuthInterceptor, networkInterceptor, VenuesApi::class.java)
    }

    private fun <T> makeService(httpAuthInterceptor: HttpFoursquareInterceptor,
                                networkInterceptor: NetworkConnectionInterceptor, obj: Class<T>): T {

        val gsonConverter = GsonConverterFactory.create(GsonFactory.getInstance())
        val loggingInterceptor = makeLoggingInterceptor(true)

        val okHttpClient = makeOkHttpClient(loggingInterceptor, networkInterceptor, httpAuthInterceptor)

        val retrofit = Retrofit.Builder()
                .baseUrl(Constants.API.FOURSQUARE)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(okHttpClient)
                .addConverterFactory(gsonConverter)
                .build()
        return retrofit.create(obj)
    }
}