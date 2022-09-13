package com.example.findvenues.data.venue

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "venue_table")
data class Venue (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val country: String,
    val address : String,
    val userId : Int
        )
