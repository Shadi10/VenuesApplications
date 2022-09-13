package com.example.findvenues.secondActivity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.findvenues.data.venue.Venue
import com.example.findvenues.data.venue.VenueDatabase
import com.example.findvenues.data.venue.VenueRepository

class DbVenuesViewModel(application: Application) : AndroidViewModel(application) {
    var venuesLiveData: LiveData<List<Venue>> = MutableLiveData()

    private val repository: VenueRepository

    init {
        val venueDao = VenueDatabase.getDatabase(application).venueDao()
        repository = VenueRepository(venueDao)

    }

    fun getData(userId: Int): LiveData<List<Venue>> {
        venuesLiveData = repository.getUserWithVenues(userId)
        return venuesLiveData
    }
}