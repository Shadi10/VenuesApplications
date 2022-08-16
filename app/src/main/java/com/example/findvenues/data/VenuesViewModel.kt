package com.example.findvenues.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.findvenues.Result
import com.example.findvenues.RetrofitBuilder
import com.example.findvenues.VenueResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VenuesViewModel(application: Application) : AndroidViewModel(application) {
    val venuesLiveData: MutableLiveData<List<Result>> = MutableLiveData()
//    val filters = MutableLiveData<Set<Filter>>()
    val map: HashMap<String, Any> = HashMap()
    private val repository: VenueRepository

    init {
        val venueDao = VenueDatabase.getDatabase(application).venueDao()
        repository = VenueRepository(venueDao)
    }

    fun addVenue(venue: Venue) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addVenue(venue)
        }
    }

    fun getData(open_at: String = "") {
        val retrofitBuilder = RetrofitBuilder()

        map["ll"] = "33.8938,35.5018"
        map["radius"] = 15000
        map["open_at"] = open_at
        map["limit"] = 20

        val retrofitData = retrofitBuilder.getInterface()
            .getDataQuery(map)

        retrofitData.enqueue(object : Callback<VenueResponse> {

            override fun onResponse(call: Call<VenueResponse>, response: Response<VenueResponse>) {

                venuesLiveData.value = response.body()?.results ?: listOf()
            }

            override fun onFailure(call: Call<VenueResponse>, t: Throwable) {
                venuesLiveData.value = listOf()
            }
        })
    }
}