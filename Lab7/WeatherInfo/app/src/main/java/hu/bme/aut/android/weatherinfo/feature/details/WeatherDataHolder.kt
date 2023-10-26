package hu.bme.aut.android.weatherinfo.feature.details

import hu.bme.aut.android.weatherinfo.model.WeatherData

interface WeatherDataHolder {
    fun getWeatherData(): WeatherData?
}