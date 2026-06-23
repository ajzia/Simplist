package com.ajzia.simplist.firebase

import com.ajzia.simplist.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
  fun getCategoriesFlow(): Flow<List<Category>>
}