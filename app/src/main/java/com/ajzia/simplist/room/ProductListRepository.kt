package com.ajzia.simplist.room

import com.ajzia.simplist.model.ProductList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductListRepository @Inject constructor(
  val productListDao: ProductListDao
) {
  val unArchivedProductLists: Flow<List<ProductList>> =
    productListDao.getAllNotArchivedProductLists()

  val archivedProductLists: Flow<List<ProductList>> =
    productListDao.getAllArchivedProductLists()

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