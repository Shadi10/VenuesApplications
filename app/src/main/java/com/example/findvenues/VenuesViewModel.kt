package com.example.findvenues

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class VenuesViewModel : ViewModel() {
    val venuesLiveData: MutableLiveData<List<Result>> = MutableLiveData()
     fun getData() {

        val retrofitBuilder = RetrofitBuilder()

        val retrofitData = retrofitBuilder.getInterface().getDataQuery(latitudeLongitude = "52.354619,4.870984", radius = 15000)
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