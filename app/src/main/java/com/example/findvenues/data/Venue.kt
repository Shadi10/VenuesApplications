package com.example.findvenues.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "venue_table")
data class Venue (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val country: String,
    val address : String
        )
data class venueData(
    val venues: List<Venue>
)