package hu.bme.aut.android.shoppinglist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ShoppingItem::class], version = 1)
@TypeConverters(value = [ShoppingItem.Category::class])
abstract class ShoppingListDatabase : RoomDatabase() {
    abstract fun shoppingItemDao(): ShoppingItemDao

    companion object {
        fun getDatabase(applicationContext: Context): ShoppingListDatabase {
            return Room.databaseBuilder(
                applicationContext,
                ShoppingListDatabase::class.java,
                "shopping-list"
            ).build();
        }
    }
}
// NEPTUN: JOYAXJ