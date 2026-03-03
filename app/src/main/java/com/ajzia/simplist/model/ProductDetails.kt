package com.ajzia.simplist.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductDetails(
  var name: String,
  var categoryId: String,
  var isChecked: Boolean = false,
  var quantity: Int = 0,
)
