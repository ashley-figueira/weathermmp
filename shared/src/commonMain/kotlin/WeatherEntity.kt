package com.ashley.weathermmp

data class WeatherEntity(
    val id: Long,
    val city: String,
    val temperature: Float,
    val condition: String,
    val wind: Double,
    val windDirection: String,
    val iconUrl: String,
    val lastUpdatedAt: String?
)