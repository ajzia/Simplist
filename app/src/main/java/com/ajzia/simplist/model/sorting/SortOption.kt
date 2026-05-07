package com.ajzia.simplist.model.sorting

data class SortOption<T : Enum<T>>(
  val filter: T,
  val mode: SortingMode
) {
  val filterName: String
    get() = "${filter.toString().lowercase()}_${mode.name.lowercase()}"
}