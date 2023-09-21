package hu.bme.aut.android.publictransport

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.android.publictransport.databinding.ActivityDetailsBinding
import java.util.Calendar
import java.util.Locale

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private var price = 0.0
    private var modifier = 1.0
    private var days = 1.0

    companion object {
        const val KEY_TRANSPORT_TYPE = "KEY_TRANSPORT_TYPE"
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val transportType = intent.getIntExtra(KEY_TRANSPORT_TYPE, -1)
        binding.tvTicketType.text = getTypeString(transportType)

        when (transportType) {
            ListActivity.TYPE_BIKE -> price = 750.0
            ListActivity.TYPE_BUS -> price = 1000.0
            ListActivity.TYPE_TRAIN -> price = 1500.0
            ListActivity.TYPE_BOAT -> price = 2500.0
        }

        binding.dpStartDate.minDate = System.currentTimeMillis() - 1000
        var startDate = Calendar.getInstance()
        var endDate = Calendar.getInstance()
        binding.dpStartDate.init(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DAY_OF_MONTH)) { _: DatePicker, year: Int, month: Int, day: Int ->
            startDate = Calendar.Builder().setDate(year, month, day).build()
            checkDateIsBefore(startDate, endDate)
            calculateDays(startDate, endDate)
            updatePrice()
        }

        binding.dpEndDate.init(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DAY_OF_MONTH)) { _: DatePicker, year: Int, month: Int, day: Int ->
            endDate = Calendar.Builder().setDate(year, month, day).build()
            checkDateIsBefore(startDate, endDate)
            calculateDays(startDate, endDate)
            updatePrice()
        }

        binding.rgPriceCategory.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.rbFullPrice.id -> modifier = 1.0
                binding.rbPublicServant.id -> modifier = 0.5
                binding.rbSenior.id -> modifier = 0.1
            }
            updatePrice()
        }

        binding.btnPurchase.setOnClickListener {
            val typeString = getTypeString(transportType)
            val dateString = "${getDateFrom(binding.dpStartDate)} - ${getDateFrom(binding.dpEndDate)}"

            val intent = Intent(this, PassActivity::class.java)
            intent.putExtra(PassActivity.KEY_TYPE_STRING, typeString)
            intent.putExtra(PassActivity.KEY_DATE_STRING, dateString)
            startActivity(intent)
        }
        updatePrice()
    }

    private fun getTypeString(transportType: Int): String {
        return when (transportType) {
            ListActivity.TYPE_BUS -> "Bus pass"
            ListActivity.TYPE_TRAIN -> "Train pass"
            ListActivity.TYPE_BIKE -> "Bike pass"
            ListActivity.TYPE_BOAT -> "Boat pass"
            else -> "Unknown pass type"
        }
    }

    private fun getDateFrom(picker: DatePicker): String {
        return String.format(
            Locale.getDefault(), "%04d.%02d.%02d.",
            picker.year, picker.month + 1, picker.dayOfMonth
        )
    }

    private fun updatePrice() {
        val finalPrice = price * modifier * days
        binding.tvPrice.text = finalPrice.toString() + " Ft"
    }

    private fun checkDateIsBefore(date1: Calendar, date2: Calendar) {
        if (date2.before(date1)) {
            binding.dpEndDate.updateDate(date1.get(Calendar.YEAR), date1.get(Calendar.MONTH), date1.get(Calendar.DAY_OF_MONTH))
        } else if (date1.after(date2)) {
            binding.dpStartDate.updateDate(date2.get(Calendar.YEAR), date2.get(Calendar.MONTH), date2.get(Calendar.DAY_OF_MONTH))
        }
    }

    private fun calculateDays(startDate: Calendar, endDate: Calendar) {
        days = ((endDate.timeInMillis - startDate.timeInMillis) / (1000 * 3600 * 24)).toDouble() + 1.0
    }
}
// JOYAXJ