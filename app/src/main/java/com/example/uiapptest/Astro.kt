package com.example.firebase

data class Astro(
    val is_moon_up: Double,
    val is_sun_up: Double,
    val moon_illumination: String,
    val moon_phase: String,
    val moonrise: String,
    val moonset: String,
    val sunrise: String,
    val sunset: String
)