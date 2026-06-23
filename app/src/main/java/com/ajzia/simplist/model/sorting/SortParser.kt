package com.ajzia.simplist.model.sorting

fun parseListFilter(filter: String): SortOption<ListFilter> {
  if (filter.isEmpty()) {
    return SortOption(ListFilter.NAME, SortingMode.DESC)
  }

  val parts = filter.split("_")

  val mode = SortingMode.entries.find {
    it.name == parts.last().uppercase()
  } ?: error("Unknown mode ${parts.last()}")

  val filterKey = parts.dropLast(1).joinToString("_")

  val parsedFilter = ListFilter.fromString(filterKey.uppercase())
    ?: error("Unknown ListFilter: $filterKey")

  return SortOption(parsedFilter, mode)
}

fun parseCategoryFilter(filter: String): SortOption<CategoryFilter> {
  if (filter.isEmpty()) {
    return SortOption(CategoryFilter.NAME, SortingMode.ASC)
  }

  val parts = filter.split("_")

  val mode = SortingMode.entries.find {
    it.name == parts.last().uppercase()
  } ?: error("Unknown mode ${parts.last()}")

  val filterKey = parts.dropLast(1).joinToString("_")

  val parsedFilter = CategoryFilter.fromString(filterKey.uppercase())
    ?: error("Unknown CategoryFilter: $filterKey")

  return SortOption(parsedFilter, mode)
}