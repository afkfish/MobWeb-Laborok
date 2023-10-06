package hu.bme.aut.android.workplaceapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import hu.bme.aut.android.workplaceapp.data.DataManager
import hu.bme.aut.android.workplaceapp.databinding.FragmentHolidayBinding

class HolidayFragment : Fragment() {
    private lateinit var binding: FragmentHolidayBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHolidayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnTakeHoliday.setOnClickListener {
            findNavController().navigate(R.id.action_holidayFragment_to_datePickerDialogFragment)
        }
        findNavController()
            .currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<DatePickerDialogFragment.DatePickerResult>(DATE_SELECTED_KEY)
            ?.observe(viewLifecycleOwner) {
                val numHolidays = DataManager.holidays
                if (DataManager.remainingHolidays > 0){
                    DataManager.holidays = numHolidays + 1
                }
                loadHolidays()
            }
        loadHolidays()
    }

    private fun loadHolidays(){
        val entries = listOf(
            PieEntry(DataManager.holidays.toFloat(), "Taken"),
            PieEntry(DataManager.remainingHolidays.toFloat(), "Remaining")
        )

        val dataSet = PieDataSet(entries, "Holidays")
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()

        val data = PieData(dataSet)
        binding.chartHoliday.data = data
        binding.chartHoliday.invalidate()

        binding.btnTakeHoliday.isEnabled = DataManager.remainingHolidays > 0
    }

    companion object {
        const val DATE_SELECTED_KEY = "date_selected"
    }
}
// NEPTUN: JOYAXJ