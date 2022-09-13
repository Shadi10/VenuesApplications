package com.example.findvenues.data.venue

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.findvenues.data.user.User
import com.example.findvenues.data.user.UserDao

@Database(entities = [Venue::class,User::class], version = 1, exportSchema = false)
abstract class VenueDatabase : RoomDatabase() {

    abstract fun venueDao(): VenueDao
    abstract fun userDao(): UserDao
    companion object {
        @Volatile
        private var INSTANCE: VenueDatabase? = null

        fun getDatabase(context: Context): VenueDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VenueDatabase::class.java,
                    "venue_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}