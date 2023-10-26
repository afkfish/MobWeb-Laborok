package hu.bme.aut.android.weatherinfo.feature.city

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.weatherinfo.R
import hu.bme.aut.android.weatherinfo.databinding.ActivityCityBinding
import hu.bme.aut.android.weatherinfo.feature.details.DetailsActivity

class CityActivity : AppCompatActivity(), CityAdapter.OnCitySelectedListener,
    AddCityDialogFragment.AddCityDialogListener {

    private lateinit var binding: ActivityCityBinding
    private lateinit var adapter: CityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initFab()
        initRecyclerView()
    }

    private fun initFab() {
        binding.fab.setOnClickListener {
            AddCityDialogFragment().show(supportFragmentManager, AddCityDialogFragment::class.java.simpleName)
        }
    }

    private fun initRecyclerView() {
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CityAdapter(this)
        adapter.addCity("Budapest")
        adapter.addCity("Debrecen")
        adapter.addCity("Sopron")
        adapter.addCity("Szeged")
        binding.mainRecyclerView.adapter = adapter
    }

    override fun onCitySelected(city: String?) {
        val showDetailsIntent = Intent()
        showDetailsIntent.setClass(this@CityActivity, DetailsActivity::class.java)
        showDetailsIntent.putExtra(DetailsActivity.EXTRA_CITY_NAME, city)
        startActivity(showDetailsIntent)
    }

    override fun onCityAdded(city: String?) {
        adapter.addCity(city!!)
    }

    override fun onCityRemoved(position: Int) {
        adapter.removeCity(position)
    }
}