package com.jordansilva.foursquarenearby.data.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jordansilva.foursquarenearby.data.repository.local.converter.RoomTypeConverters
import com.jordansilva.foursquarenearby.domain.model.POI

@Database(entities = [POI::class], version = 1, exportSchema = false)
@TypeConverters(RoomTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun poiDao(): POIDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): AppDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            AppDatabase::class.java,
                            "foursquarenearby.db")
                            .build()
                }
                return INSTANCE!!
            }
        }
    }
}