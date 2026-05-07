package com.ajzia.simplist.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
  val LIST_FILTER = stringPreferencesKey("list_filter")
  val CATEGORY_FILTER = stringPreferencesKey("category_filter")
}