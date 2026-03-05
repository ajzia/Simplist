package com.ajzia.simplist.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ajzia.simplist.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

  @Insert
  suspend fun insertProduct(product: Product)

  @Delete
  suspend fun deleteProduct(product: Product)

  @Query("SELECT * FROM products")
  fun getAllProducts(): Flow<List<Product>>
}