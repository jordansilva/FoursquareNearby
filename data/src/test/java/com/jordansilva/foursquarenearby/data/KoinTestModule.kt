package com.jordansilva.foursquarenearby.data

import androidx.room.Room
import com.jordansilva.foursquarenearby.data.repository.POIDataRepository
import com.jordansilva.foursquarenearby.data.repository.local.AppDatabase
import org.koin.dsl.module.module

/**
 * In-Memory Room Database definition
 */
val roomTestModule = module {
    single {
        // In-Memory database config
        Room.inMemoryDatabaseBuilder(get(), AppDatabase::class.java)
                .allowMainThreadQueries()
                .build()
    }
}

val repositoryTestModule = module {
    single {  }
    single { POIDataRepository(get(), get()) }
}