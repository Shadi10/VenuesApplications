package com.example.findvenues.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface VenueDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addVenue(venue: Venue)

    @Query("SELECT * FROM venue_table")
    fun getAllData():LiveData<List<Venue>>
}