package com.ajzia.simplist.room

import com.ajzia.simplist.model.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepository @Inject constructor(
  val roomDao: ProductListDao
) {
  val products: Flow<List<Product>> =
    roomDao.getAllProducts()

  suspend fun insertProduct(product: Product) {
    return roomDao.insertProduct(product)
  }

  suspend fun deleteProduct(product: Product) {
    return roomDao.deleteProduct(product)
  }
}