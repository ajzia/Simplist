package com.ajzia.simplist.room

import com.ajzia.simplist.model.ProductList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductListRepository @Inject constructor(
  val roomDao: ProductListDao
) {
  val unArchivedProductLists: Flow<List<ProductList>> =
    roomDao.getAllNotArchivedProductLists()

  val archivedProductLists: Flow<List<ProductList>> =
    roomDao.getAllArchivedProductLists()

  fun getProductList(id: Int): Flow<ProductList?> =
    roomDao.getProductList(id)


  suspend fun insertProductList(productList: ProductList) {
    roomDao.insertProductList(productList)
  }

  suspend fun updateProductList(productList: ProductList) {
    roomDao.updateProductList(productList)
  }

  suspend fun deleteProductList(productList: ProductList) {
    roomDao.deleteProductList(productList)
  }

}