package com.jordansilva.foursquarenearby.data.repository.mapper

import com.jordansilva.foursquarenearby.data.repository.remote.foursquare.model.VenueResponse
import com.jordansilva.foursquarenearby.domain.model.POI

object POIMapper : Mapper<VenueResponse, POI> {
    override fun mapToDomain(type: VenueResponse): POI {
        return POI(id = type.id,
                name = type.name,
                description = type.description,
                contact = type.contact?.formattedPhone ?: "",
                rating = type.rating)
                .apply {
                    category = type.categories.find { it.primary }?.shortName
                    categories = ArrayList(type.categories.map { it.shortName })

                    address = POI.Address(type.location.address, type.location.lat, type.location.lng,
                            type.location.city, type.location.state,
                            type.location.cc, type.location.country)
                }
    }

}