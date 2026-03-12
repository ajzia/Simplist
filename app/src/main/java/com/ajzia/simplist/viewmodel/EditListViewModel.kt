package com.ajzia.simplist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajzia.simplist.model.ProductDetails
import com.ajzia.simplist.model.ProductList
import com.ajzia.simplist.room.ProductListRepository
import com.ajzia.simplist.room.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditListViewModel @Inject constructor(
  private val repository: ProductListRepository,
  private val productRepository: ProductRepository,
): ViewModel() {

  val products = productRepository.products

  fun getProductList(listId: Int): Flow<ProductList?> {
    return if (listId == -1) {
      flowOf(null)
    } else {
      repository.getProductList(listId)
    }
  }

  fun insertProductList(list: ProductList) {
    viewModelScope.launch {
      var details = assignCategoriesToProducts(list.productsDetails)
      details = sortProductsByCategory(details)

      repository.insertProductList(
        list.copy(productsDetails = details)
      )
    }
  }

  fun updateProductList(list: ProductList) {
    viewModelScope.launch {
      var details = assignCategoriesToProducts(list.productsDetails)
      details = sortProductsByCategory(details)

      repository.updateProductList(
        list.copy(productsDetails = details)
      )
    }
  }

  private suspend fun assignCategoriesToProducts(
    productsDetails: List<ProductDetails>
  ): List<ProductDetails> {
    val products = productRepository.getAllProductsOnce()
      .associateBy { it.name }
    val details = productsDetails.toMutableList()

    details.forEach { det ->
      if (det.categoryId != Int.MAX_VALUE)
        return@forEach

      products[det.name]?.let { product ->
        det.categoryId = product.categoryId
      }
    }

    return details
  }

  private fun sortProductsByCategory(
    details: List<ProductDetails>
  ): List<ProductDetails> {
    if (details.isEmpty())
      return details

    return details.sortedWith(
      compareBy({ it.categoryId }, { it.name })
    )
  }

}
