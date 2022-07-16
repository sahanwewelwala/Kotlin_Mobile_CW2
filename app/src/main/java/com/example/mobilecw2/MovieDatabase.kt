package com.example.mobilecw2

import androidx.room.Database
import androidx.room.RoomDatabase

//Room Database
@Database(entities = [MovieEntity::class], version = 1)
 abstract class MovieDatabase : RoomDatabase() {
     abstract fun movieDao(): MovieDao
}