package com.ajzia.simplist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajzia.simplist.firebase.FirestoreRepository
import com.ajzia.simplist.model.Category
import com.ajzia.simplist.model.Product
import com.ajzia.simplist.room.ProductListRepository
import com.ajzia.simplist.room.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
  private val productRepository: ProductRepository,
  private val productListRepository: ProductListRepository,
  private val firestoreRepository: FirestoreRepository
): ViewModel() {

  private val _categories = MutableStateFlow<List<Category>>(emptyList())
  val categories: StateFlow<List<Category>> get() = _categories

  val productsRoom = productRepository.products

  init {
    fetchCategories()
  }

  fun fetchCategories() {
    viewModelScope.launch {
      firestoreRepository.getCategoriesFlow()
        .catch { println("Error in fetching categories") }
        .collect { categories ->
          _categories.value = categories
          println("Categories updated")
        }
    }
  }

  fun insertProduct(product: Product) {
    viewModelScope.launch {
      productRepository.insertProduct(product)

      assignCategoryToProduct(
        name = product.name,
        categoryId = product.categoryId
      )
    }
  }

  fun deleteProduct(product: Product) {
    viewModelScope.launch {
      productRepository.deleteProduct(product)

      assignCategoryToProduct(
        name = product.name,
        categoryId = Int.MAX_VALUE
      )
    }
  }

  private suspend fun assignCategoryToProduct(
    name: String,
    categoryId: Int,
  ) {
    val lists = productListRepository.getAllProductListsOnce()

    lists.forEach { list ->
      if (list.productsDetails.none { it.name == name }) return@forEach

      val details = list.productsDetails.map { detail ->
        if (detail.name == name) detail.copy(categoryId = categoryId)
        else detail
      }.sortedWith(compareBy({ it.categoryId }, { it.name }))

      productListRepository.updateProductList(
        list.copy(productsDetails = details)
      )
    } // forEach
  }
}