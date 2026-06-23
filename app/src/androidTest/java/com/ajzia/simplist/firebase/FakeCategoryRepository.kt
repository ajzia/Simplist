package com.ajzia.simplist.firebase

import com.ajzia.simplist.model.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeCategoryRepository : CategoryRepository {

  override fun getCategoriesFlow(): Flow<List<Category>> {
    return flowOf(
      listOf(
        Category(id = 1, name = "Category 1"),
        Category(id = 2, name = "Category 2"),
        Category(id = 3, name = "Category 3"),
        Category(id = 4, name = "Category 4"),
        Category(id = 5, name = "Category 5"),
        Category(id = 6, name = "Category 6"),
      )
    )
  }
}