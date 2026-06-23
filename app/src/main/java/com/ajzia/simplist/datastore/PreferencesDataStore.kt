package com.ajzia.simplist.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesDataStore @Inject constructor(
  private val dataStore: DataStore<Preferences>
) {

  fun getListFilterFlow(): Flow<String> = dataStore.data.map {
    it[PreferencesKeys.LIST_FILTER] ?: "last_modified_desc"
  }

  suspend fun setListFilter(filter: String) {
    if (filter.isEmpty()) return
    dataStore.edit {
      it[PreferencesKeys.LIST_FILTER] = filter
    }
  }

  fun getCategoryFilterFlow(): Flow<String> = dataStore.data.map {
    it[PreferencesKeys.CATEGORY_FILTER] ?: "id_asc"
  }

  suspend fun setCategoryFilter(filter: String) {
    if (filter.isEmpty()) return
    dataStore.edit {
      it[PreferencesKeys.CATEGORY_FILTER] = filter
    }
  }

}