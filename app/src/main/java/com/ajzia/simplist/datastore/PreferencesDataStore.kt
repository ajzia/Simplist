package com.ajzia.simplist.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesDataStore @Inject constructor(
  @ApplicationContext private val context: Context
) {

  fun listFilterFlow(): Flow<String> = context.dataStore.data.map {
    it[PreferencesKeys.LIST_FILTER] ?: "last_modified_desc"
  }

  suspend fun setListFilter(filter: String) {
    context.dataStore.edit {
      it[PreferencesKeys.LIST_FILTER] = filter
    }
  }

  fun categoryFilterFlow(): Flow<String> = context.dataStore.data.map {
    it[PreferencesKeys.CATEGORY_FILTER] ?: "id_asc"
  }

  suspend fun setCategoryFilter(filter: String) {
    context.dataStore.edit {
      it[PreferencesKeys.CATEGORY_FILTER] = filter
    }
  }

}