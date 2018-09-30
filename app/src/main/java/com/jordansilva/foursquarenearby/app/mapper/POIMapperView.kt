package com.jordansilva.foursquarenearby.app.mapper

import com.jordansilva.foursquarenearby.domain.model.POI
import com.jordansilva.foursquarenearby.app.model.POIView

class POIMapperView : MapperView<POI, POIView> {

    override fun mapFromDomain(type: POI): POIView {
        return POIView(id = type.id,
                name = type.name,
                description = type.description,
                location = type.address.toString(),
                category = type.category,
                categories = type.categories,
                photo = null)
    }
}