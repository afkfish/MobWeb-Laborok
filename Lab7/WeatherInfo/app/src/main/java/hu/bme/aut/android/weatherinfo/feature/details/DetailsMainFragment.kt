package hu.bme.aut.android.weatherinfo.feature.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import hu.bme.aut.android.weatherinfo.databinding.FragmentDetailsMainBinding

class DetailsMainFragment : Fragment() {

    private lateinit var binding: FragmentDetailsMainBinding
    private var weatherDataHolder: WeatherDataHolder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weatherDataHolder = if (activity is WeatherDataHolder) {
            activity as WeatherDataHolder?
        } else {
            throw RuntimeException(
                "Activity must implement WeatherDataHolder interface!"
            )
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        binding = FragmentDetailsMainBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (weatherDataHolder?.getWeatherData() != null) {
            displayWeatherData()
        }
    }

    private fun displayWeatherData() {
        val weather = weatherDataHolder?.getWeatherData()?.weather?.first()
        binding.tvMain.text = weather?.main
        binding.tvDescription.text = weather?.description

        Glide.with(this)
            .load("https://openweathermap.org/img/w/${weather?.icon}.png")
            .transition(DrawableTransitionOptions().crossFade())
            .into(binding.ivIcon)
    }
}
