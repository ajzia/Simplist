package com.ajzia.simplist.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajzia.simplist.firebase.FirestoreRepository
import com.ajzia.simplist.model.Category
import com.ajzia.simplist.model.Product
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

  fun insertProduct(product: Product) =
    viewModelScope.launch {
      productRepository.insertProduct(product)
    }

  fun deleteProduct(product: Product) =
    viewModelScope.launch {
      productRepository.deleteProduct(product)
    }

  // is this even necessary?
  fun getCategoryProducts(
    list: List<Product>, categoryId: String
  ): List<Product> {
    return list.filter { it.categoryId == categoryId }
  }
}