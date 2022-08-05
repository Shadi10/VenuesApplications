package com.example.findvenues

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiInterface {
    @Headers("Authorization:fsq3Jsd+KzSushA/LfoTYoZjvDRRbFav3d7WGuoK+VXI8Rs=")
    @GET("v3/places/search")
    fun getDataQuery(@Query("ll") latitudeLongitude:String, @Query("radius") radius:Int ):Call<VenueResponse>
}



