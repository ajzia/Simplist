package com.ajzia.simplist.room

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.ajzia.simplist.model.ProductDetails
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@ProvidedTypeConverter
class ProductListConverter {

  @TypeConverter
  fun BooleanToInt(value: Boolean): Int {
    return if (value) 1 else 0
  }

  @TypeConverter
  fun IntToBoolean(value: Int): Boolean {
    return value == 1
  }

  @TypeConverter
  fun ProductDetailsListToString(productDetails: List<ProductDetails>): String {
    return Json.encodeToString<List<ProductDetails>>(productDetails)
  }

  @TypeConverter
  fun StringToProductDetailsList(value: String): List<ProductDetails> {
    return Json.decodeFromString<List<ProductDetails>>(value)
  }
}
