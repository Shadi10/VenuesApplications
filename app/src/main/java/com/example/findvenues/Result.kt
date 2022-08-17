package com.example.findvenues


data class Result(
    val name : String,
    val location: Location,
    val geocodes: GeoCodes,
    val fsq_id: String
)
data class VenueResponse(
    val results: List<Result>
)

