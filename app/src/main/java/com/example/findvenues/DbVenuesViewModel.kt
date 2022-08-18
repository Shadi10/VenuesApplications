package com.example.findvenues

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.findvenues.Result
import com.example.findvenues.data.Venue
import com.example.findvenues.data.VenueDatabase
import com.example.findvenues.data.VenueRepository

class DbVenuesViewModel(application: Application) : AndroidViewModel(application) {
    var venuesLiveData: LiveData<List<Venue>> = MutableLiveData()
    private val repository: VenueRepository

    init {
        val venueDao = VenueDatabase.getDatabase(application).venueDao()
       repository = VenueRepository(venueDao)
        venuesLiveData = repository.getAllData()
    }

    fun getData():LiveData<List<Venue>>{
        return venuesLiveData
    }
}