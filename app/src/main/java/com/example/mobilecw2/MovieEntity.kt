package com.example.mobilecw2

import androidx.room.Entity
import androidx.room.PrimaryKey
//Creating database  in room library columns in String
@Entity
data class MovieEntity(
    //Primary key of database my databse
    @PrimaryKey val title: String,
    val year: String?,
    val rated:String?,
    val released: String?,
    val runtime: String?,
    val genre: String?,
    val director: String?,
    val writer: String?,
    val actors: String?,
    val plot: String?
)
