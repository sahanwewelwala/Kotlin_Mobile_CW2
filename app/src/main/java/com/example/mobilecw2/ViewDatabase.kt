package com.example.mobilecw2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.room.Room
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ViewDatabase : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_database)

        val tvDb = findViewById<TextView>(R.id.tvDb)
        tvDb.setText("")

        val db = Room.databaseBuilder(this, MovieDatabase ::class.java,"mydatabase").build()
        val movieDao = db.movieDao()


        runBlocking {
            launch {
                //tvDb.text = movieDao.getAll().toString()
                val movies: List<MovieEntity> = movieDao.getAll()
                for (M in movies) {
                    tvDb.append("\n Tittle : ${M.title} \n Year : ${M.year} \n Rating : ${M.rated}\n Released Date : ${M.released} \n Runtime : ${M.runtime}\n Genre ${M.genre} \n Director : ${M.director} \n Writer : ${M.writer} \n Actors :${M.actors} \n Plot : ${M.plot} \n")
                }
            }
        }
    }
}
