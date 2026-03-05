package com.ajzia.simplist.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ajzia.simplist.model.Product
import com.ajzia.simplist.model.ProductList

@Database(
  entities = [ProductList::class, Product::class],
  exportSchema = false,
  version = 1,
)
@TypeConverters(
  ProductListConverter::class
)
abstract class ProductListDatabase : RoomDatabase() {
  abstract fun productListDao(): ProductListDao
  abstract fun productDao(): ProductDao

  companion object {
    @Volatile
    var INSTANCE: ProductListDatabase? = null

    fun getDatabase(context: Context): ProductListDatabase {
      return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          ProductListDatabase::class.java,
          "product_list_database"
        ).addTypeConverter(ProductListConverter())
        .build()

        INSTANCE = instance
        instance
      }
    }
  }
}