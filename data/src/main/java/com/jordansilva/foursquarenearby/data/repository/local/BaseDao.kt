package com.jordansilva.foursquarenearby.data.repository.local

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<in T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg obj: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: List<T>)

    @Delete
    fun delete(obj: T): Int

    @Update
    fun update(obj: T): Int

}