package hu.bme.aut.android.weatherinfo.model

data class WeatherData (
    var coord: Coord,
    var weather: List<Weather>? = null,
    var base: String,
    var main: MainWeatherData? = null,
    var visibility: Int,
    var wind: Wind? = null,
    var clouds: Cloud,
    var dt: Int,
    var sys: Sys,
    var timezone: Int,
    var id: Int,
    var name: String,
    var cod: Int
)