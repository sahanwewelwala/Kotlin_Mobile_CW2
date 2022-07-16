package com.example.mobilecw2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_search_movies.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.json.JSONTokener
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class SearchMovies : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_movies)


        val tv1 = findViewById<EditText>(R.id.tv1)//SearchMovie Edit Text



        val retrieveMovieBtn = findViewById<Button>(R.id.b4) //Add retrieveMovie Button
        val saveMovieBtn = findViewById<Button>(R.id.b5) //Search Search Movies Button

        val stb = StringBuilder()
        retrieveMovieBtn.setOnClickListener {
            val editValue = tv1.text.toString() //textedit
            val urlString = "https://www.omdbapi.com/?t=$editValue&apikey=d6999af3";
            val url = URL(urlString)
            val con: HttpURLConnection = url.openConnection() as HttpURLConnection

            try{
            runBlocking {

                launch {
                    // run the code of the coroutine in a new thread
                    stb.clear()
                    withContext(Dispatchers.IO) {
                        var bf = BufferedReader(InputStreamReader(con.inputStream))
                        var line: String? = bf.readLine()
                        while (line != null) {
                            stb.append(line + "\n")
                            line = bf.readLine()
                        }

                    }
                    parseJSON(stb)
                }

            }
        } catch (e: Exception){
            tvError.setText("Enter a Valid Input!")
        }
        }

        saveMovieBtn.setOnClickListener {
            val editValue = tv1.text.toString() //textedit
            val urlString = "https://www.omdbapi.com/?t=$editValue&apikey=7e9943d6";
            val url = URL(urlString)
            val con: HttpURLConnection = url.openConnection() as HttpURLConnection

            try{
                runBlocking {

                    launch {
                        // run the code of the coroutine in a new thread
                        stb.clear()
                        withContext(Dispatchers.IO) {
                            var bf = BufferedReader(InputStreamReader(con.inputStream))
                            var line: String? = bf.readLine()
                            while (line != null) {
                                stb.append(line + "\n")
                                line = bf.readLine()
                            }
                        }
                        parseJSON(stb)
                        saveMovie(stb)
                        val toast = Toast.makeText(applicationContext, "Movie Have Been Saved Successfully", Toast.LENGTH_LONG)
                        toast.show()
                    }
                }
            } catch (e: Exception){
                tvError.setText("Enter a Valid Input!")
            }
        }
    }

            suspend fun parseJSON(stb: java.lang.StringBuilder) {

                val tv2 = findViewById<TextView>(R.id.tv2)
                //getting data
                val jsonObject = JSONTokener(stb.toString()).nextValue() as JSONObject
                val movieDetails = java.lang.StringBuilder()
                val title = jsonObject.getString("Title")
                val year = jsonObject.getString("Year")
                val rated = jsonObject.getString("Rated")
                val released = jsonObject.getString("Released")
                val runtime = jsonObject.getString("Runtime")
                val genre = jsonObject.getString("Genre")
                val director = jsonObject.getString("Director")
                val writer = jsonObject.getString("Writer")
                val actors = jsonObject.getString("Actors")
                val plot = jsonObject.getString("Plot")

                //movie details to preview
                movieDetails.append("Title: \"$title\" \n")
                movieDetails.append("Year: \"$year\" \n")
                movieDetails.append("Rated: \"$rated\" \n")
                movieDetails.append("Released: \"$released\" \n")
                movieDetails.append("Runtime: \"$runtime\" \n")
                movieDetails.append("Genre: \"$genre\" \n")
                movieDetails.append("Director: \"$director\" \n")
                movieDetails.append("Writer: \"$writer\" \n")
                movieDetails.append("Actor: \"$actors\" \n")
                movieDetails.append("plot: \"$plot\" \n")

                tv2.text = movieDetails

            }
    //saving movies to database
    private suspend fun saveMovie(stb: java.lang.StringBuilder){
        val tv3 = findViewById<TextView>(R.id.tv3)//Testing Purpose

        val db = Room.databaseBuilder(this, MovieDatabase::class.java,"mydatabase").build()
        val movieDao = db.movieDao()
        val jsonObject = JSONTokener(stb.toString()).nextValue() as JSONObject

        val title = jsonObject.getString("Title")
        val year = jsonObject.getString("Year")
        val rated = jsonObject.getString("Rated")
        val released = jsonObject.getString("Released")
        val runtime = jsonObject.getString("Runtime")
        val genre = jsonObject.getString("Genre")
        val director = jsonObject.getString("Director")
        val writer = jsonObject.getString("Writer")
        val actors = jsonObject.getString("Actors")
        val plot = jsonObject.getString("Plot")



        //Inserting Values
        movieDao.insertMovies(MovieEntity(title, year, rated, released, runtime, genre, director, writer, actors, plot))
        val movies: List<MovieEntity> = movieDao.getAll()

    }
}



