package com.example.firebase

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApiInstance {
    val api:apiInterface by lazy {

        Retrofit.Builder()
            .baseUrl("https://weatherapi-com.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()

    }


}