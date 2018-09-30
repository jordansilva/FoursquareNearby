package com.jordansilva.foursquarenearby.data.repository.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.jordansilva.foursquarenearby.infrastructure.util.extensions.fromJson
import java.util.*


object RoomTypeConverters {

    @TypeConverter
    @JvmStatic
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    @JvmStatic
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    @JvmStatic
    fun toListString(value: String?): ArrayList<String>? {
        return value?.let {
            return Gson().fromJson(value)
        }
    }

    @TypeConverter
    @JvmStatic
    fun fromListString(value: ArrayList<String>): String? {
        return Gson().toJson(value)
    }
}