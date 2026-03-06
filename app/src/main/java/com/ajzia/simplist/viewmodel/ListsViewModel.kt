package com.ajzia.simplist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajzia.simplist.firebase.FirestoreRepository
import com.ajzia.simplist.model.Category
import com.ajzia.simplist.model.ProductList
import com.ajzia.simplist.room.ProductListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListsViewModel @Inject constructor(
  private val repository: ProductListRepository,
  private val firestoreRepository: FirestoreRepository
): ViewModel() {

  val lists = repository.unArchivedProductLists
  val archivedLists = repository.archivedProductLists

  private val _categories = MutableStateFlow<List<Category>>(emptyList())
  val categories: StateFlow<List<Category>> get() = _categories

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

  private fun updateProductList(list: ProductList) =
    viewModelScope.launch {
      repository.updateProductList(list)
    }

  fun deleteProductList(list: ProductList) =
    viewModelScope.launch {
      repository.deleteProductList(list)
    }

  fun onCheckClick(list: ProductList, index: Int) {
    val updatedDetails = list.productsDetails.mapIndexed { i, item ->
      if (i == index) {
        item.copy(isChecked = !item.isChecked)
      } else item
    }

    updateProductList(list.copy(productsDetails = updatedDetails))
  }

  fun onCheckAll(list: ProductList, indexes: List<Int>, value: Boolean) {
    val updatedDetails = list.productsDetails.mapIndexed { i, item ->
      if (i in indexes) {
        item.copy(isChecked = value)
      } else item
    }

    updateProductList(list.copy(productsDetails = updatedDetails))
  }

  fun onArchive(list: ProductList) {
    updateProductList(list.copy(isArchived = !list.isArchived))
  }

  fun onCopy(list: ProductList){//, clipboard: ClipboardManager?) {
    val text = "$** {list.name} **\n"

    for (product in list.productsDetails) {
      text.plus("- ${product.name}")

      val quantity = product.quantity
      if (quantity != 0) {
        text.plus(" x${quantity}")
      }
      text.plus("\n")
    }

//    val clip: ClipData = ClipData.newPlainText("Product list", text)
//    clipboard?.setPrimaryClip(clip)
  }


  // TODO: use this in top bar 2.0
  fun filterListBy(filter: String, lists: List<ProductList>): List<ProductList> {
    when(filter) {
      "date_created_asc"    -> return lists.sortedBy { it.dateCreated }
      "date_created_desc"   -> return lists.sortedByDescending { it.dateCreated }
      "last_edited_asc"     -> return lists.sortedBy { it.lastEdited }
      "last_edited_desc"    -> return lists.sortedByDescending { it.lastEdited }
      "alphabetically_asc"  -> return lists.sortedBy { it.name }
      "alphabetically_desc" -> return lists.sortedByDescending { it.name }
    }
    return lists
  } // filterListBy

  // TODO: use this in top bar 2.0
  fun searchLists(query: String, lists: List<ProductList>): List<ProductList> {
    val _lists: MutableList<ProductList> = mutableListOf()

    for (list in lists) {
      if (list.name.contains(query))
        _lists.add(list)
    }

    return _lists
  }

}