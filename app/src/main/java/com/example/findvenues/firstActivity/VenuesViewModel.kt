package com.example.findvenues.firstActivity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.findvenues.data.user.User
import com.example.findvenues.data.user.UserRepository
import com.example.findvenues.data.venue.Venue
import com.example.findvenues.data.venue.VenueDatabase
import com.google.android.gms.maps.model.Marker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VenuesViewModel(application: Application) : AndroidViewModel(application) {

    val venuesLiveData: MutableLiveData<List<Result>> = MutableLiveData()
    var clickedVenue: Result? = null
    var marker: Marker? = null
    private val map: HashMap<String, Any> = HashMap()
    private val repository: UserRepository
    private var userList: List<User> = listOf()

    init {
        val userDao = VenueDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
    }

    fun addVenue(venue: Venue) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addVenue(venue)
        }
    }

    fun getLl(): String {
        val lat = marker?.position?.latitude
        val long = marker?.position?.longitude
        return "$lat,$long"
    }


    fun getData(
        ll: String = "33.8547,35.8623",
        radius: String = "10000",
        limit: String = "5",
        openAt: String = ""
    ) {
        val retrofitBuilder = RetrofitBuilder()

        map["ll"] = ll
        map["radius"] = radius
        map["limit"] = limit
        map["open_at"] = openAt
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