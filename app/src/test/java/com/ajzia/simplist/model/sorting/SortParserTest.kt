package com.ajzia.simplist.model.sorting

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class SortParserTest {

  @Test
  fun parseListFilter_emptyFilter() {
    val filter = ""
    assertThat(parseListFilter(filter))
      .isEqualTo(SortOption(ListFilter.NAME, SortingMode.DESC))
  }

  @Test
  fun parseListFilter_wrongMode() {
    val filter = "date_created_wrongMode"

    val e:  IllegalStateException =
      assertThrows(IllegalStateException::class.java){
        parseListFilter(filter) }
    assertThat(e).hasMessageThat().contains("Unknown mode wrongMode")
  }

  @Test
  fun parseListFilter_wrongName() {
    val filter = "wrong_list_filter_asc"

    val e:  IllegalStateException =
      assertThrows(IllegalStateException::class.java) {
        parseListFilter(filter) }
    assertThat(e).hasMessageThat().contains("Unknown ListFilter: wrong_list_filter")
  }

  @Test
  fun parseListFilter_correctParsing() {
    val filter = "last_modified_asc"

    assertThat(parseListFilter(filter)).isEqualTo(
      SortOption(ListFilter.LAST_MODIFIED, SortingMode.ASC)
    )
  }


  @Test
  fun parseCategoryFilter_emptyFilter() {
    val filter = ""
    assertThat(parseCategoryFilter(filter))
      .isEqualTo(SortOption(CategoryFilter.NAME, SortingMode.ASC))
  }

  @Test
  fun parseCategoryFilter_wrongMode() {
    val filter = "name_wrongMode"

    val e:  IllegalStateException =
      assertThrows(IllegalStateException::class.java) {
        parseCategoryFilter(filter) }
    assertThat(e).hasMessageThat().contains("Unknown mode wrongMode")
  }

  @Test
  fun parseCategoryFilter_wrongName() {
    val filter = "wrong_category_filter_desc"

    val e:  IllegalStateException =
      assertThrows(IllegalStateException::class.java){
        parseCategoryFilter(filter) }
    assertThat(e).hasMessageThat().contains("Unknown CategoryFilter: wrong_category_filter")
  }

  @Test
  fun parseCategoryFilter() {
    val filter = "id_desc"

    assertThat(parseCategoryFilter(filter)).isEqualTo(
      SortOption(CategoryFilter.ID, SortingMode.DESC)
    )
  }
}