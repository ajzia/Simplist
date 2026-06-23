package com.ajzia.simplist.datastore

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.flow.first
import org.junit.jupiter.api.io.TempDir
import org.junit.jupiter.api.Test
import java.io.File

class PreferencesDataStoreTest {

  @TempDir
  lateinit var tempFolder: File

  @Test
  fun getListFilterFlow_empty() = runTest {
    val dataStore = PreferenceDataStoreFactory.create(
      scope = this,
      produceFile = { File(tempFolder, "test_store.preferences_pb") },
    )
    val storage = PreferencesDataStore(dataStore)

    val result = storage.getListFilterFlow().first()
    assertThat(result).isEqualTo("last_modified_desc")
  }

  @Test
  fun setAndGetListFilter_emptyFilter() = runTest {
    val dataStore = PreferenceDataStoreFactory.create(
      scope = this,
      produceFile = { File(tempFolder, "test_store.preferences_pb") },
    )
    val storage = PreferencesDataStore(dataStore)

    storage.setListFilter("")
    val result = storage.getListFilterFlow().first()
    assertThat(result).isEqualTo("last_modified_desc")
  }

  @Test
  fun setAndGetListFilter_correctFilter() = runTest {
    val dataStore = PreferenceDataStoreFactory.create(
      scope = this,
      produceFile = { File(tempFolder, "test_store.preferences_pb") },
    )
    val storage = PreferencesDataStore(dataStore)

    storage.setListFilter("date_modifier_desc")
    val result = storage.getListFilterFlow().first()
    assertThat(result).isEqualTo("date_modifier_desc")
  }

  @Test
  fun getCategoryFilterFlow_empty() = runTest {
    val dataStore = PreferenceDataStoreFactory.create(
      scope = this,
      produceFile = { File(tempFolder, "test_store.preferences_pb") },
    )
    val storage = PreferencesDataStore(dataStore)

    val result = storage.getCategoryFilterFlow().first()
    assertThat(result).isEqualTo("id_asc")
  }

  @Test
  fun setAndGetCategoryFilter_emptyFilter() = runTest {
    val dataStore = PreferenceDataStoreFactory.create(
      scope = this,
      produceFile = { File(tempFolder, "test_store.preferences_pb") },
    )
    val storage = PreferencesDataStore(dataStore)

    storage.setCategoryFilter("")
    val result = storage.getCategoryFilterFlow().first()
    assertThat(result).isEqualTo("id_asc")
  }

  @Test
  fun setAndGetCategoryFilter_correctFilter() = runTest {
    val dataStore = PreferenceDataStoreFactory.create(
      scope = this,
      produceFile = { File(tempFolder, "test_store.preferences_pb") },
    )
    val storage = PreferencesDataStore(dataStore)

    storage.setCategoryFilter("id_desc")
    val result = storage.getCategoryFilterFlow().first()
    assertThat(result).isEqualTo("id_desc")
  }

}