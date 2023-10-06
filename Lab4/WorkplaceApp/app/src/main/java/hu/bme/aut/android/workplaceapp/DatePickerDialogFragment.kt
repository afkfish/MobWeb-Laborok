package hu.bme.aut.android.workplaceapp

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import java.io.Serializable
import java.util.Calendar

class DatePickerDialogFragment : DialogFragment(), DatePickerDialog.OnDateSetListener{

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        // disable past dates
        val datePickerDialog = DatePickerDialog(requireContext(), this, year, month, day)
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        return datePickerDialog
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val result = DatePickerResult(year, month, dayOfMonth)
        findNavController()
            .previousBackStackEntry
            ?.savedStateHandle
            ?.set(HolidayFragment.DATE_SELECTED_KEY, result)
    }

    data class DatePickerResult(
        val year: Int,
        val month: Int,
        val day: Int
    ): Serializable
}
// NEPTUN: JOYAXJ