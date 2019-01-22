package com.ashley.weathermmp

class WeatherEntityMapper {

    fun mapFrom(from: WeatherResponse): WeatherEntity {
        val id = from.id ?: throw IllegalArgumentException("Weather does not exist!")
        val city = from.name ?: throw IllegalArgumentException("City does not exist!")
        val temperature = from.main?.temp ?: throw IllegalArgumentException("Temperature does not exist!")
        val condition = from.weather?.first()?.main ?: throw IllegalArgumentException("Condition does not exist!")
        val wind = from.wind?.speed ?: throw IllegalArgumentException("Wind speed does not exist!")
        val windDirection = from.wind.deg ?: throw IllegalArgumentException("Wind direction does not exist!")
        val iconUrl =  from.weather.first().icon?.let { "$WEATHER_ICON_BASE_URL$it.png" } ?:  ""
        val lastUpdatedAt = from.dt?.toString()

        return WeatherEntity(id.toLong(), city, convertKelvinInCelsius(temperature), condition, wind, convertDegreesToDirection(windDirection), iconUrl, lastUpdatedAt)
    }

    /**
     * Convert kelvin to celcius
     * @param kelvin - temp in kelvin
     * @return celcius
     */
    private fun convertKelvinInCelsius(kelvin: Double): Float {
        return (kelvin - 273.15).toFloat()
    }

    private fun convertDegreesToDirection(windDegree: Int): String {
        return when {
            windDegree >= 45 && windDegree < 90   -> WindDirection.NorthEast.direction
            windDegree >= 90 && windDegree < 135  -> WindDirection.East.direction
            windDegree >= 135 && windDegree < 180 -> WindDirection.SouthEast.direction
            windDegree >= 180 && windDegree < 225 -> WindDirection.South.direction
            windDegree >= 225 && windDegree < 270 -> WindDirection.SouthWest.direction
            windDegree >= 270 && windDegree < 315 -> WindDirection.West.direction
            windDegree >= 315 && windDegree < 360 -> WindDirection.NorthWest.direction
            else -> WindDirection.North.direction
        }
    }

    companion object {
        private const val WEATHER_ICON_BASE_URL = "http://openweathermap.org/img/w/"
    }
}