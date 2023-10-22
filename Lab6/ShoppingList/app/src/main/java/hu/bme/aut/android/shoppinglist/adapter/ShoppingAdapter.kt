package hu.bme.aut.android.shoppinglist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.shoppinglist.R
import hu.bme.aut.android.shoppinglist.data.ShoppingItem
import hu.bme.aut.android.shoppinglist.databinding.ItemShoppingListBinding

class ShoppingAdapter(private val listener: ShoppingItemClickListener) :
    RecyclerView.Adapter<ShoppingAdapter.ShoppingViewHolder>() {

    private val items = mutableListOf<ShoppingItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ShoppingViewHolder(
        ItemShoppingListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ShoppingViewHolder, position: Int) {
        val shoppingItem = items[position]

        holder.binding.ivIcon.setImageResource(getImageResource(shoppingItem.category))
        holder.binding.cbIsBought.isChecked = shoppingItem.isBought
        holder.binding.tvName.text = shoppingItem.name
        holder.binding.tvDescription.text = shoppingItem.description
        holder.binding.tvCategory.text = shoppingItem.category.name
        holder.binding.tvPrice.text = "${shoppingItem.estimatedPrice} Ft"

        holder.binding.cbIsBought.setOnCheckedChangeListener { buttonView, isChecked ->
            shoppingItem.isBought = isChecked
            listener.onItemChanged(shoppingItem)
        }
    }

    @DrawableRes()
    private fun getImageResource(category: ShoppingItem.Category): Int {
        return when (category) {
            ShoppingItem.Category.FOOD -> R.drawable.groceries
            ShoppingItem.Category.ELECTRONIC -> R.drawable.lightning
            ShoppingItem.Category.BOOK -> R.drawable.open_book
        }
    }

    fun addItem(item: ShoppingItem) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun getItem(position: Int): ShoppingItem {
        return items[position]
    }

    fun deleteItem(item: ShoppingItem) {
        val position = items.indexOf(item)
        items.remove(item)
        notifyItemRemoved(position)
    }

    fun update(shoppingItems: List<ShoppingItem>) {
        items.clear()
        items.addAll(shoppingItems)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    interface ShoppingItemClickListener {
        fun onItemChanged(item: ShoppingItem)
        fun onItemDeleted(position: Int)
    }

    inner class ShoppingViewHolder(val binding: ItemShoppingListBinding) : RecyclerView.ViewHolder(binding.root), OnClickListener {
        init {
            binding.ibRemove.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onItemDeleted(adapterPosition)
        }
    }
}
// NEPTUN: JOYAXJ