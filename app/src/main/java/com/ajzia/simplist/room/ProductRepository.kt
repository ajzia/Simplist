package com.ajzia.simplist.room

import com.ajzia.simplist.model.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepository @Inject constructor(
  val productDao: ProductDao
) {
  val products: Flow<List<Product>> =
    productDao.getAllProducts()

  suspend fun insertProduct(product: Product) {
    return productDao.insertProduct(product)
  }

  suspend fun deleteProduct(product: Product) {
    return productDao.deleteProduct(product)
  }
}