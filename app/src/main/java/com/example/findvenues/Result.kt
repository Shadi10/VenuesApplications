package com.example.findvenues


data class Result(
    val name: String,
    val location: Location,
    val geocodes: GeoCodes,
    val fsq_id: String
)

data class VenueResponse(
    val results: List<Result>
)

data class Location(
    val address: String,
    val country: String
)

data class GeoCodes(
    val main: Main
)
data class Main(
    val latitude: String,
    val longitude:String
)
