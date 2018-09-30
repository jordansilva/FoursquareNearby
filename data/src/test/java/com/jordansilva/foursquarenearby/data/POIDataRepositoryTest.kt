package com.jordansilva.foursquarenearby.data

import com.jordansilva.foursquarenearby.data.repository.remote.interceptor.NetworkConnectionInterceptor
import com.jordansilva.foursquarenearby.domain.repository.POIRepository
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertNotSame
import kotlinx.coroutines.experimental.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.standalone.StandAloneContext.loadKoinModules
import org.koin.standalone.StandAloneContext.stopKoin
import org.koin.standalone.inject
import org.koin.test.KoinTest

class POIDataRepositoryTest : KoinTest {

    val poiRepository: POIRepository by inject()
    val networkInterceptor: NetworkConnectionInterceptor by inject()

    @Before
    fun before() {
        loadKoinModules(roomTestModule)
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun `list nearby places by location`() {
        val location = "Rotterdam"
        val radius = 1000
        val limit = 50

        val list = runBlocking { poiRepository.getNearbyPOIs(location, radius, limit) }
        val item = list[0]

        assertNotSame(0, list.size)
        assertNotNull(item)
    }
}