package com.jordansilva.foursquarenearby.data.repository.local

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<in T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(vararg obj: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(obj: List<T>)

    @Delete
    fun delete(obj: T): Int

    @Update
    fun update(obj: T): Int

}