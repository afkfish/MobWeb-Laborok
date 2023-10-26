package hu.bme.aut.android.weatherinfo.feature.city

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatDialogFragment
import hu.bme.aut.android.weatherinfo.R
import hu.bme.aut.android.weatherinfo.databinding.DialogNewCityBinding

class AddCityDialogFragment : AppCompatDialogFragment() {

    private lateinit var binding: DialogNewCityBinding
    private lateinit var listener: AddCityDialogListener

    interface AddCityDialogListener {
        fun onCityAdded(city: String?)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        binding = DialogNewCityBinding.inflate(LayoutInflater.from(context))

        listener = context as? AddCityDialogListener
            ?: throw RuntimeException("Activity must implement the AddCityDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.new_city)
            .setView(binding.root)
            .setPositiveButton(R.string.ok) { _, _ ->
                listener.onCityAdded(
                    binding.NewCityDialogEditText.text.toString()
                )
            }
            .setNegativeButton(R.string.cancel, null)
            .create()
    }
}
// NEPTUN: JOYAXJ