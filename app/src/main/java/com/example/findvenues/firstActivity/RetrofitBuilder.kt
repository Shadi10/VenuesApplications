package com.example.findvenues.firstActivity

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
const val BASE_URL = "https://api.foursquare.com"
class RetrofitBuilder() {
    fun getInterface(): ApiInterface {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)
    }

}