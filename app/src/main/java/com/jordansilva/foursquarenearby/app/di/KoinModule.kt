package com.jordansilva.foursquarenearby.app.di

import androidx.room.RoomDatabase
import com.jordansilva.foursquarenearby.app.mapper.MapperView
import com.jordansilva.foursquarenearby.app.mapper.POIMapperView
import com.jordansilva.foursquarenearby.app.model.POIView
import com.jordansilva.foursquarenearby.app.ui.poi.POIViewModel
import com.jordansilva.foursquarenearby.data.repository.POIDataRepository
import com.jordansilva.foursquarenearby.data.repository.local.AppDatabase
import com.jordansilva.foursquarenearby.data.repository.remote.foursquare.FoursquareServiceFactory
import com.jordansilva.foursquarenearby.data.repository.remote.foursquare.VenuesApi
import com.jordansilva.foursquarenearby.data.repository.remote.interceptor.HttpFoursquareInterceptor
import com.jordansilva.foursquarenearby.data.repository.remote.interceptor.NetworkConnectionInterceptor
import com.jordansilva.foursquarenearby.domain.interactor.poi.GetNearbyPOIsUseCase
import com.jordansilva.foursquarenearby.domain.interactor.poi.GetPOIUseCase
import com.jordansilva.foursquarenearby.domain.model.POI
import com.jordansilva.foursquarenearby.domain.repository.POIRepository
import com.jordansilva.foursquarenearby.infrastructure.util.factory.GsonFactory
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

object KoinModule {

    val ViewModule = module {
        //viewModel { MainActivityViewModel(get(), get()) }

        //POI details and POIs list viewModel injection
        viewModel { POIViewModel(get(), get(), get("poiViewMapper")) }

        //Mappers
        factory("poiViewMapper") { POIMapperView() as MapperView<POI, POIView> }
    }

    val UseCaseModule = module {
        factory { GetNearbyPOIsUseCase(get()) }
        factory { GetPOIUseCase(get()) }

        //Gson instance
        single { GsonFactory.getInstance() }
    }

    val RepositoryModule = module {
        single { AppDatabase.getInstance(get()) as RoomDatabase }

        //Dao
        factory { AppDatabase.getInstance(get()).poiDao() }

        //Repositories
        factory { POIDataRepository(get(), get()) } bind POIRepository::class
    }

    val ApiModule = module {
        //Interceptors
        factory { NetworkConnectionInterceptor(get()) }
        factory { HttpFoursquareInterceptor() }

        //APIs
        factory { FoursquareServiceFactory.makeVenuesApiService(get(), get()) } bind VenuesApi::class

    }
}