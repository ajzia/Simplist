package com.ajzia.simplist.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ajzia.simplist.model.Product
import com.ajzia.simplist.model.ProductList
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductListDao {

  /* ProductList queries */
  @Insert
  suspend fun insertProductList(productList: ProductList)

  @Update
  suspend fun updateProductList(productList: ProductList)

  @Delete
  suspend fun deleteProductList(productList: ProductList)


  @Query("SELECT * FROM product_lists WHERE isArchived == 0")
  fun getAllNotArchivedProductLists(): Flow<List<ProductList>>

  @Query("SELECT * FROM product_lists WHERE isArchived == 1")
  fun getAllArchivedProductLists(): Flow<List<ProductList>>

  @Query("SELECT * FROM product_lists WHERE id = :id LIMIT 1")
  fun getProductList(id: Int): Flow<ProductList?>


  /* Product queries */
  @Insert
  suspend fun insertProduct(product: Product)

  @Delete
  suspend fun deleteProduct(product: Product)

  @Query("SELECT * FROM products")
  fun getAllProducts(): Flow<List<Product>>
}