package com.example.mobilecw2

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao

//Interface Movie DAO
interface MovieDao {
    //Query to get all data
    @Query("Select * from MovieEntity")
    suspend fun getAll(): List<MovieEntity>

    //query to insert data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(vararg movieEntity: MovieEntity)

    //Delete Data in movie Database
    @Query("DELETE FROM MovieEntity")
    suspend fun deleteAll()

    //Select Title name from movie entity
    @Query("Select title from MovieEntity WHERE actors LIKE '%' || :value || '%' ")
    suspend fun findActors(value: String): List<String>

}