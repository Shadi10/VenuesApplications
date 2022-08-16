package com.example.findvenues.data

class VenueRepository(private val venueDao: VenueDao) {
    suspend fun addVenue(venue:Venue){
        venueDao.addVenue(venue)
    }
}