package hu.bme.aut.android.weatherinfo.feature.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.bme.aut.android.weatherinfo.databinding.FragmentDetailsMoreBinding

class DetailsMoreFragment : Fragment() {

    private lateinit var binding: FragmentDetailsMoreBinding
    private var weatherDataHolder: WeatherDataHolder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weatherDataHolder = if (activity is WeatherDataHolder) {
            activity as WeatherDataHolder?
        } else {
            throw RuntimeException("Activity must implement WeatherDataHolder interface!")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailsMoreBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (weatherDataHolder?.getWeatherData() != null) {
            showWeatherData()
        }
    }

    private fun showWeatherData() {
        val weatherData = weatherDataHolder!!.getWeatherData()
        binding.tvTemperature.text = weatherData?.main?.temp.toString()
        binding.tvMinTemp.text = weatherData?.main?.temp_min.toString()
        binding.tvMaxTemp.text = weatherData?.main?.temp_max.toString()
        binding.tvPressure.text = weatherData?.main?.pressure.toString()
        binding.tvHumidity.text = weatherData?.main?.humidity.toString()
    }
}