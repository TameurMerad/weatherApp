package com.example.uiapptest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase.ApiInstance
import com.example.firebase.Hour
import com.example.firebase.myAdapter
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    var adapter: myAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val nextThreeDays = findViewById<TextView>(R.id.lastThreeDays)
        val input = findViewById<EditText>(R.id.textInputId)
        val time = LocalTime.now()
        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("h:mm a")
        val day = today.dayOfWeek.toString()
        val month = today.month.toString()
        val timeCurrent = time.format(formatter)
        val conditionTxt = findViewById<TextView>(R.id.conditionId)
        val conditionImg = findViewById<ImageView>(R.id.imgId)
        val dateTxt = findViewById<TextView>(R.id.dateId)
        val tempCurrent = findViewById<TextView>(R.id.tempCurrentId)
        val HandL = findViewById<TextView>(R.id.HandLid)
        val windSpeed = findViewById<TextView>(R.id.wind_speed)
        val rain = findViewById<TextView>(R.id.rain)
        val humidity = findViewById<TextView>(R.id.humidity)






        nextThreeDays.setOnClickListener {
            startActivity(Intent(this,secondActivity::class.java))

        }








        input.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)
            ) {
                GlobalScope.launch(Dispatchers.IO) {

                    val response = try {
                        ApiInstance.api.getWeather(input.text.toString(), 3)
                    } catch (e: Exception) {
                        runOnUiThread {
                            Toast.makeText(applicationContext, "${e.message}", Toast.LENGTH_LONG).show()
                        }
//                    text.append(e.message)
                        Log.d("nmi", "${e.message} ")
                        return@launch
                    }
                    if (response.isSuccessful) {
                        runOnUiThread {
                            val forecast = response.body()!!
                            val uriImg = "https:" + forecast.current.condition.icon
                            conditionTxt.text = forecast.current.condition.text.toString()
                            tempCurrent.text = forecast.current.temp_c.toInt().toString() + "°"
                            HandL.text = "H:${forecast.location.lon}  L:${forecast.location.lat}"
                            windSpeed.text = "${forecast.current.wind_kph}km/h"
                            humidity.text = forecast.current.humidity.toInt().toString() + "%"
                            rain.text = forecast.current.precip_mm.toString() + "%"
                            dateTxt.text = "$day $month  | ${forecast.location.localtime.takeLast(5)}"
                            buildRecyclerView(forecast.forecast.forecastday[0].hour)
                            Log.d("nmi image", "${uriImg}")
                            Picasso.get()
                                .load(uriImg)
                                .into(conditionImg)

                            nextThreeDays.setOnClickListener {
                                val intent = Intent(this@MainActivity,secondActivity::class.java)
                                intent.putExtra("searchName",forecast.location.name)
                                startActivity(intent)


                            }
                        }
                    }



                }

                true
            } else {
                false
            }
        }








    }


    private fun buildRecyclerView(list : List<Hour>){
        val recyclerView = findViewById<RecyclerView>(R.id.myRecyclerViewId)
        adapter = myAdapter(list)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false
        )
    }
}