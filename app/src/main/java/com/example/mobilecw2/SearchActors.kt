package com.example.mobilecw2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.room.Room
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SearchActors : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_actors)

        //buttons on search actirs class
        val btnSearch = findViewById<Button>(R.id.b6)
        val searchActor = findViewById<EditText>(R.id.searchEt)
        val tvSearch = findViewById<TextView>(R.id.tvSearch)

        //searching string name of the actor in the
        val db = Room.databaseBuilder(this,MovieDatabase::class.java,"mydatabase").build()
        val movieDao = db.movieDao()

        btnSearch.setOnClickListener {
            val value = searchActor.text.toString()
            runBlocking {
                launch {
                    val movieName = java.lang.StringBuilder()
                    var movieNameList : List<String> = movieDao.findActors(value).toList() //Creating a List for Corresponding Movie Names
                    for (i in movieNameList){
                        movieName.append("•$i\n")
                    }
                    //tvSearch.text = movieDao.findActors(value).toString()
                    tvSearch.text = movieName
                }
            }
        }
    }
}