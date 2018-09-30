package com.jordansilva.foursquarenearby.data.repository.mapper

interface Mapper<T, D> {
    fun mapToDomain(type: T): D
}
