package hu.bme.aut.android.weatherinfo.model

data class MainWeatherData(
    val temp: Float = 0f,
    val pressure: Float = 0f,
    val humidity: Float = 0f,
    val temp_min: Float = 0f,
    val temp_max: Float = 0f
)
