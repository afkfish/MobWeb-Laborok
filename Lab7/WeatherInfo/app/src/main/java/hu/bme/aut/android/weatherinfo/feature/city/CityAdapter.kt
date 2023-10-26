package hu.bme.aut.android.weatherinfo.feature.city

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.weatherinfo.R
import hu.bme.aut.android.weatherinfo.databinding.ItemCityBinding

class CityAdapter(private val listener: OnCitySelectedListener) : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {
    private val cities: MutableList<String> = ArrayList()

    interface OnCitySelectedListener {
        fun onCitySelected(city: String?)
        fun onCityRemoved(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false)
        return CityViewHolder(view)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val item = cities[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = cities.size

    fun addCity(newCity: String) {
        cities.add(newCity)
        notifyItemInserted(cities.size - 1)
    }

    fun removeCity(position: Int) {
        cities.removeAt(position)
        notifyItemRemoved(position)
        if (position < cities.size) {
            notifyItemRangeChanged(position, cities.size - position)
        }
    }

    inner class CityViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding = ItemCityBinding.bind(itemView)
        var item: String? = null

        init {
            binding.root.setOnClickListener { listener.onCitySelected(item) }
            binding.CityItemRemoveButton.setOnClickListener { listener.onCityRemoved(adapterPosition) }
        }

        fun bind(newCity: String?) {
            item = newCity
            binding.CityItemNameTextView.text = item
        }
    }
}
// NEPTUN: JOYAXJ