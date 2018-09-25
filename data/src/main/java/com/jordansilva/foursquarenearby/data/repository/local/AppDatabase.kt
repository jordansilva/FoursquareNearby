package com.jordansilva.foursquarenearby.data.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [], version = 1)
//@TypeConverters(RoomTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {

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