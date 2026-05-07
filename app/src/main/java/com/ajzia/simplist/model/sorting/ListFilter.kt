package com.ajzia.simplist.model.sorting

enum class ListFilter {
  LAST_MODIFIED, DATE_CREATED, NAME;

  companion object {
    fun fromString(value: String): ListFilter? =
      entries.find { it.name == value }
  }
}