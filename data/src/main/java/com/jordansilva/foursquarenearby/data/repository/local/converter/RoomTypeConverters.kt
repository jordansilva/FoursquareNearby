package com.jordansilva.foursquarenearby.data.repository.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.jordansilva.foursquarenearby.infrastructure.util.extensions.fromJson

object RoomTypeConverters {

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