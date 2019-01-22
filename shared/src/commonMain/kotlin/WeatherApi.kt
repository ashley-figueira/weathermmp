package com.ashley.weathermmp

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.url
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.JSON

internal expect val ApplicationDispatcher: CoroutineDispatcher

@ImplicitReflectionSerializer
class WeatherApi {

    private val httpClient = HttpClient()

    fun fetchWeather(successCallback: (WeatherEntity) -> Unit, errorCallback: (Exception) -> Unit) {
        GlobalScope.apply {
            launch(ApplicationDispatcher) {
                try {
                    val weatherString = httpClient.get<String> { url("$baseUrl/weather?q=Paris&appid=aa70fe8bb6787240885f37132d549cea") }
                    val weather = JSON(strictMode = false).parse(WeatherResponse.serializer(), weatherString)
                    val weatherEntity = WeatherEntityMapper().mapFrom(weather)
                    successCallback(weatherEntity)
                } catch (e: Exception) {
                    errorCallback(e)
                }
            }
        }
    }

    companion object {
        private const val baseUrl = "http://api.openweathermap.org/data/2.5"
    }
}