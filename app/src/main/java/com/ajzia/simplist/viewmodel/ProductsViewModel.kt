package com.ajzia.simplist.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajzia.simplist.model.Product
import com.ajzia.simplist.room.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
  private val productRepository: ProductRepository,
): ViewModel() {

  val productsRoom = productRepository.products

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