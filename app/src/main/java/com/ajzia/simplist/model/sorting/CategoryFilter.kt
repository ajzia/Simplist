package com.ajzia.simplist.model.sorting

enum class CategoryFilter {
  ID, NAME;

  companion object {
    fun fromString(value: String): CategoryFilter? =
      entries.find { it.name == value }
  }
}