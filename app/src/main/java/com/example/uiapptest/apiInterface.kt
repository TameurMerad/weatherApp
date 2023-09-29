package com.example.firebase

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface apiInterface {

    @GET("forecast.json")
    @Headers(
        "X-RapidAPI-Key: 44bd04199emshcb0161e29a0f76ep132533jsnd5af03ac69c4",
        "X-RapidAPI-Host: weatherapi-com.p.rapidapi.com"
    )
    suspend fun getWeather(@Query("q") q: String, @Query("days") d:Int): Response<weather_class>

}