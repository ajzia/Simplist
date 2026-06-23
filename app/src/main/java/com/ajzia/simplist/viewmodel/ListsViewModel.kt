package com.ajzia.simplist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajzia.simplist.datastore.PreferencesDataStore
import com.ajzia.simplist.firebase.CategoryRepository
import com.ajzia.simplist.model.Category
import com.ajzia.simplist.model.ProductList
import com.ajzia.simplist.room.ProductListRepository
import com.ajzia.simplist.screens.utils.sortedByLocale
import com.ajzia.simplist.screens.utils.sortedByLocaleDescending
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class ListsViewModel @Inject constructor(
  private val repository: ProductListRepository,
  private val categoryRepository: CategoryRepository,
  private val preferencesDataStore: PreferencesDataStore
): ViewModel() {

  private val _searchText = MutableStateFlow("")
  val searchText = _searchText.asStateFlow()

  val filter = preferencesDataStore.listFilterFlow()

  private val _lists = MutableStateFlow(repository.lists)
  val lists = combine(
      searchText.debounce(100L),
      _lists.value,
      filter
    ) { text, lists, flt ->
      val filtered = lists.filter {
        text.isBlank() || it.doesMatchQuery(text)
      }

      filterListBy(flt, filtered)
    }
    .stateIn(
      viewModelScope,
      SharingStarted.WhileSubscribed(5000),
      emptyList()
    )

  fun osSearchTextChange(text: String) {
    _searchText.value = text
  }

  fun onFilterChange(filter: String) {
    viewModelScope.launch {
      preferencesDataStore.setListFilter(filter)
    }
  }

  private val _categories = MutableStateFlow<List<Category>>(emptyList())
  val categories: StateFlow<List<Category>> get() = _categories

  init {
    fetchCategories()
  }

  fun fetchCategories() {
    viewModelScope.launch {
      categoryRepository.getCategoriesFlow()
        .catch { println("Error in fetching categories") }
        .collect { categories ->
          _categories.value = categories
          println("Categories updated")
        }
    }
  }

  private fun updateProductList(list: ProductList) =
    viewModelScope.launch {
      repository.updateProductList(list.copy(
        lastEdited = System.currentTimeMillis()
      ))
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

  private fun filterListBy(
    filter: String,
    lists: List<ProductList>
  ): List<ProductList> {
    when(filter) {
      "date_created_asc"    -> return lists.sortedBy { it.dateCreated }
      "date_created_desc"   -> return lists.sortedByDescending { it.dateCreated }
      "last_modified_asc"   -> return lists.sortedBy { it.lastEdited }
      "last_modified_desc"  -> return lists.sortedByDescending { it.lastEdited }
      "name_asc"            -> return lists.sortedByLocale { it.name }
      "name_desc"           -> return lists.sortedByLocaleDescending { it.name }
    }
    return lists
  }

}