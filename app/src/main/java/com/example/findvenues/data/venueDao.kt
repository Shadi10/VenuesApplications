package com.example.findvenues.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface VenueDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addVenue(venue: Venue)

}