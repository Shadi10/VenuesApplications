package com.example.findvenues

data class Result(
    val name : String,
    val location: Location
)
data class VenueResponse(
    val results: List<Result>
)