package com.example.findvenues


data class Result(
    val name : String,
    val location: Location,
    val geocodes: GeoCodes
)
data class VenueResponse(
    val results: List<Result>
)

