package com.ajzia.simplist.room

import com.ajzia.simplist.model.ProductDetails
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class ProductListConverterTest {

  var converter = ProductListConverter()

  @Test
  fun booleanToInt_correctType() {
    val value = true
    val result = converter.BooleanToInt(value)

    assertThat(result).isInstanceOf(Int::class.java)
    assertThat(1).isEqualTo(result)
  }

  @Test
  fun intToBoolean_correctType() {
    val value = 0
    val result = converter.IntToBoolean(value)

    assertThat(result).isInstanceOf(Boolean::class.java)
    assertThat(false).isEqualTo(result)
  }

  @Test
  fun productDetailsListToString_correctType() {
    val productDetails = listOf(
      ProductDetails("Potato", 2, false, 0),
      ProductDetails("Bread", 5, false, 5),
      ProductDetails("Kiwi", 1, true, 1),
    )
    val result = converter.ProductDetailsListToString(productDetails)

    assertThat(result).isInstanceOf(String::class.java)
  }

  @Test
  fun stringToProductDetailsList_correctType() {
    val text = "[{\"name\":\"Potato\",\"categoryId\":2},{\"name\"" +
        ":\"Strawberry\",\"categoryId\":1,\"isChecked\":true,\"quantity\":9}]"

    val result = converter.StringToProductDetailsList(text)

    assertThat(result).isInstanceOf(List::class.java)
    assertThat(result).isEqualTo(listOf(
      ProductDetails("Potato", 2, false, 0),
      ProductDetails("Strawberry", 1, true, 9)
    ))
  }
}