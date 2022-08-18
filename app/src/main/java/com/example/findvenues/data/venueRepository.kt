package com.example.findvenues.data

import androidx.lifecycle.LiveData

class VenueRepository(private val venueDao: VenueDao) {

    suspend fun addVenue(venue:Venue){
        venueDao.addVenue(venue)
    }
    fun getAllData(): LiveData<List<Venue>> {
        return venueDao.getAllData()
    }
}