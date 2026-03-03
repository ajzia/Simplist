package com.ajzia.simplist.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajzia.simplist.model.ProductDetails
import com.ajzia.simplist.model.ProductList
import com.ajzia.simplist.room.ProductListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditListViewModel @Inject constructor(
  private val repository: ProductListRepository,
): ViewModel() {

  fun getProductList(listId: Int): Flow<ProductList?> {
    return if (listId == -1) {
      flowOf(null)
    } else {
      repository.getProductList(listId)
    }
  }

  fun insertProductList(list: ProductList) {
    val details = prepareDetails(list.productsDetails)

    viewModelScope.launch {
      repository.insertProductList(
        list.copy(productsDetails = details)
      )
    }
  }

  fun updateProductList(list: ProductList) {
    val details = prepareDetails(list.productsDetails)

    viewModelScope.launch {
      repository.updateProductList(
        list.copy(productsDetails = details)
      )
    }
  }


  private fun prepareDetails(
    productsDetails: List<ProductDetails>
  ): List<ProductDetails> {
    val details = assignCategoriesToProducts(productsDetails)
    return sortProductsByCategory(details)
  }

  private fun assignCategoriesToProducts(
    productsDetails: List<ProductDetails>
  ): List<ProductDetails> {
    // TODO

    return productsDetails
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
