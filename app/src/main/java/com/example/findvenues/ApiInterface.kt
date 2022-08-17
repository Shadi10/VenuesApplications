package com.example.findvenues

import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    @Headers("Authorization:fsq3Jsd+KzSushA/LfoTYoZjvDRRbFav3d7WGuoK+VXI8Rs=")
    @GET("v3/places/search")
    @JvmSuppressWildcards
    fun getDataQuery(
        @QueryMap map: Map<String, Any>
    ): Call<VenueResponse>
}



