package com.example.mobilecw2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_results_of_movie.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class ResultsOfMovie : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results_of_movie)
        //using intent get changes in orientation data
        var MovieName = intent.getStringExtra("MovieName")
        val stb = StringBuilder()
        val stb1 = StringBuilder()
        var xx = 0

        //getting data from website as json file
        for (i in 1..10) {
            val url_string = "https://www.omdbapi.com/?s=$MovieName&apikey=d6999af3&page=$i";
            val url = URL(url_string)
            val con: HttpURLConnection = url.openConnection() as HttpURLConnection
            try {
                runBlocking {
                    launch {
                        stb.clear()
                        withContext(Dispatchers.IO) {
                            val bf = BufferedReader(InputStreamReader(con.inputStream))
                            var line: String? = bf.readLine()
                            while (line != null) {
                                stb.append(line + "\n")
                                line = bf.readLine()
                            }
                        }
                        stb1.append(parseJSON(stb))
                        /*val jsonObject = JSONTokener(stb.toString()).nextValue() as JSONObject
                        ss = jsonObject.getString("totalResults")*/

                    }
                }
            } catch (e: Exception) {

            }
            ++xx
            //break
        }

        resultOfMovieTV1.text = stb1
        //resultOfMovieTV2.text = xx.toString() //Tested for no.of Loops
    }

    suspend fun parseJSON(stb: java.lang.StringBuilder): StringBuilder {
        // this contains the full JSON returned by the Web Service
        val json = JSONObject(stb.toString())
        // Information about all the books extracted by this function
        var allMovies = java.lang.StringBuilder()
        var jsonArray: JSONArray = json.getJSONArray("Search")
        // extract all the books from the JSON array
        for (i in 0 until jsonArray.length()) {
            val movie: JSONObject = jsonArray[i] as JSONObject // this is a json object
            // extract the title
            val title = movie["Title"] as String
            //val title = volInfo["title"] as String
            allMovies.append("* $title ")
            allMovies.append("\n\n")

        }
        return allMovies
    }
}
