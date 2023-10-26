package hu.bme.aut.android.weatherinfo.feature.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.tabs.TabLayoutMediator
import hu.bme.aut.android.weatherinfo.R
import hu.bme.aut.android.weatherinfo.databinding.ActivityDetailsBinding
import hu.bme.aut.android.weatherinfo.model.WeatherData
import hu.bme.aut.android.weatherinfo.network.NetworkManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsActivity : AppCompatActivity(), WeatherDataHolder {

    private lateinit var binding: ActivityDetailsBinding
    private var city: String? = null
    private var weatherData: WeatherData? = null

    companion object {
        private const val TAG = "DetailsActivity"
        const val EXTRA_CITY_NAME = "extra.city_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        city = intent.getStringExtra(EXTRA_CITY_NAME)

        supportActionBar?.title = getString(R.string.weather, city)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResume() {
        super.onResume()
        val detailsPagerAdapter = DetailsPagerAdapter(this)
        binding.mainViewPager.adapter = detailsPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.mainViewPager) { tab, position ->
            tab.text = when(position) {
                0 -> getString(R.string.main)
                1 -> getString(R.string.details)
                else -> ""
            }
        }.attach()

        loadWeatherData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getWeatherData(): WeatherData? {
        return weatherData
    }

    private fun loadWeatherData() {
        NetworkManager.getWeather(city)?.enqueue(object : Callback<WeatherData?> {
            override fun onResponse(
                call: Call<WeatherData?>,
                response: Response<WeatherData?>
            ) {
                Log.d(TAG, "onResponse: " + response.code())
                if (response.isSuccessful) {
                    displayWeatherData(response.body())
                } else {
                    Toast.makeText(this@DetailsActivity, "Error: " + response.message(), Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(
                call: Call<WeatherData?>,
                throwable: Throwable
            ) {
                throwable.printStackTrace()
                Toast.makeText(this@DetailsActivity, "Network request error occured, check LOG", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun displayWeatherData(receivedWeatherData: WeatherData?) {
        weatherData = receivedWeatherData
        val detailsPagerAdapter = DetailsPagerAdapter(this)
        binding.mainViewPager.adapter = detailsPagerAdapter
    }
}
// NEPTUN: JOYAXJ