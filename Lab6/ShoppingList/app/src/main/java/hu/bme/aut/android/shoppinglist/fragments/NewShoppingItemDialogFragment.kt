package hu.bme.aut.android.shoppinglist.fragments

import androidx.appcompat.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.shoppinglist.R
import hu.bme.aut.android.shoppinglist.data.ShoppingItem
import hu.bme.aut.android.shoppinglist.databinding.DialogNewShoppingItemBinding

class NewShoppingItemDialogFragment : DialogFragment() {
    interface NewShoppingItemDialogListener {
        fun onShoppingItemCreated(newItem: ShoppingItem)
    }

    private lateinit var listener: NewShoppingItemDialogListener

    private lateinit var binding: DialogNewShoppingItemBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NewShoppingItemDialogListener
            ?: throw RuntimeException("Activity must implement the NewShoppingItemDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogNewShoppingItemBinding.inflate(LayoutInflater.from(context))
        binding.spCategory.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(R.array.category_items)
        )

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.new_shopping_item)
            .setView(binding.root)
            .setPositiveButton(R.string.button_ok) { dialogInterface, i ->
                if (isValid()) {
                    listener.onShoppingItemCreated(getShoppingItem())
                }
            }
            .setNegativeButton(R.string.button_cancel, null)
            .create()
    }

    private fun isValid() = binding.etName.text.isNotEmpty()

    private fun getShoppingItem() = ShoppingItem(
        name = binding.etName.text.toString(),
        description = binding.etDescription.text.toString(),
        estimatedPrice = binding.etEstimatedPrice.text.toString().toIntOrNull() ?: 0,
        category = ShoppingItem.Category.getByOrdinal(binding.spCategory.selectedItemPosition)
            ?: ShoppingItem.Category.BOOK,
        isBought = binding.cbAlreadyPurchased.isChecked
    )

    companion object {
        const val TAG = "NewShoppingItemDialogFragment"
    }
}
// NEPTUN: JOYAXJ