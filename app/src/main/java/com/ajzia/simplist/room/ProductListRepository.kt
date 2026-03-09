package com.ajzia.simplist.room

import com.ajzia.simplist.model.ProductList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductListRepository @Inject constructor(
  val productListDao: ProductListDao
) {
  val lists: Flow<List<ProductList>> =
    productListDao.getAllProductLists()

  suspend fun getAllProductListsOnce(): List<ProductList> =
    productListDao.getAllProductListsOnce()

  fun getProductList(id: Int): Flow<ProductList?> =
    productListDao.getProductList(id)

  suspend fun insertProductList(productList: ProductList) {
    productListDao.insertProductList(productList)
  }

  suspend fun updateProductList(productList: ProductList) {
    productListDao.updateProductList(productList)
  }

  suspend fun deleteProductList(productList: ProductList) {
    productListDao.deleteProductList(productList)
  }

}