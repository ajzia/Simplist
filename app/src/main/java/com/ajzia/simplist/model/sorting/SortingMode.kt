package com.ajzia.simplist.model.sorting

enum class SortingMode {
  ASC, DESC;

  fun reverse(): SortingMode =
    if (this == ASC) DESC else ASC
}