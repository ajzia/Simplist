package com.ajzia.simplist.model.sorting

import org.junit.Test
import com.google.common.truth.Truth.assertThat

class SortOptionTest {

  @Test
  fun getListFilterName_correctName() {
    val sortOption = SortOption(ListFilter.LAST_MODIFIED, SortingMode.ASC)
    assertThat(sortOption.filterName).isEqualTo("last_modified_asc")
  }

  @Test
  fun getCategoryFilterName_correctName() {
    val sortOption = SortOption(CategoryFilter.ID, SortingMode.DESC)
    assertThat(sortOption.filterName).isEqualTo("id_desc")
  }
}