package com.example.uiapptest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase.ApiInstance
import com.example.firebase.Forecastday
import com.example.firebase.Hour
import com.example.firebase.myAdapter
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class secondActivity : AppCompatActivity() {

    private var adapter:forcastAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val backBtn =findViewById<ImageView>(R.id.backBtn)
        val imageIcon =findViewById<ImageView>(R.id.tommorowIcon)
        val degree = findViewById<TextView>(R.id.degreeID)
        val condition = findViewById<TextView>(R.id.conditionn)
        val windSpeed = findViewById<TextView>(R.id.wind_speed)
        val rain = findViewById<TextView>(R.id.rain)
        val humidity = findViewById<TextView>(R.id.humidity)

        val search = intent.getStringExtra("searchName")

        backBtn.setOnClickListener{
            onBackPressed()
        }


        GlobalScope.launch(Dispatchers.IO){
            val response = try {
                ApiInstance.api.getWeather(search!!, 3)
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(applicationContext, "${e.message}", Toast.LENGTH_LONG).show()
                }
                Log.d("nmi", "${e.message} ")
                return@launch
            }
            if (response.isSuccessful){
                runOnUiThread {
                    val forcast = response.body()!!
                    val tommorow = forcast.forecast.forecastday[1]
                    Picasso.get()
                        .load("https:" + forcast.forecast.forecastday[1].day.condition.icon)
                        .into(imageIcon)

                    degree.text = tommorow.day.avgtemp_c.toInt().toString() + "°"
                    condition.text = tommorow.day.condition.text
                    windSpeed.text = tommorow.day.maxwind_kph.toString() +"km/h"
                    humidity.text = tommorow.day.avghumidity.toString()+"%"
                    rain.text = tommorow.day.daily_chance_of_rain.toString()+"%"

                    buildRecyclerView(forcast.forecast.forecastday)

                }





            }


        }







    }

    private fun buildRecyclerView(list : List<Forecastday>){
        val recyclerView = findViewById<RecyclerView>(R.id.forcastRecyclerV)
        adapter = forcastAdapter(list)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}