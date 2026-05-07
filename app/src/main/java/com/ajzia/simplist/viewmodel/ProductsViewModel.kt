package com.ajzia.simplist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajzia.simplist.datastore.PreferencesDataStore
import com.ajzia.simplist.firebase.FirestoreRepository
import com.ajzia.simplist.model.Category
import com.ajzia.simplist.model.Product
import com.ajzia.simplist.room.ProductListRepository
import com.ajzia.simplist.room.ProductRepository
import com.ajzia.simplist.screens.utils.sortedByLocale
import com.ajzia.simplist.screens.utils.sortedByLocaleDescending
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
  private val productRepository: ProductRepository,
  private val productListRepository: ProductListRepository,
  private val firestoreRepository: FirestoreRepository,
  private val preferencesDataStore: PreferencesDataStore
): ViewModel() {

  val products = productRepository.products

  val filter = preferencesDataStore.categoryFilterFlow()

  private val _categories = MutableStateFlow<List<Category>>(emptyList())
  val categories =
    filter.combine(_categories) { flt, categories ->
      filterCategoryBy(flt, categories)
    }
    .stateIn(
      viewModelScope,
      SharingStarted.WhileSubscribed(5000),
      emptyList()
    )

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

  fun onFilterChange(filter: String) {
    viewModelScope.launch {
      preferencesDataStore.setCategoryFilter(filter)
    }
  }

  fun insertProduct(name: String, categoryId: Int) {
    val productName = name.trim()
      .lowercase().replaceFirstChar { c -> c.uppercase() }

    val product = Product(name = productName, categoryId = categoryId)

    viewModelScope.launch {
      productRepository.insertProduct(product)

      assignCategoryToProduct(
        name = productName,
        categoryId = categoryId
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

  private fun filterCategoryBy(
    filter: String,
    lists: List<Category>
  ): List<Category> {
    when(filter) {
      "id_asc"    -> return lists.sortedBy { it.id }
      "id_desc"   -> return lists.sortedByDescending { it.id }
      "name_asc"  -> return lists.sortedByLocale { it.name }
      "name_desc" -> return lists.sortedByLocaleDescending { it.name }
    }
    return lists
  }

}