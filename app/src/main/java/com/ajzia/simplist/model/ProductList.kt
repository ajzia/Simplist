package com.ajzia.simplist.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_lists")
data class ProductList(
  @PrimaryKey(autoGenerate = true)
  val id: Int = 0,
  var name: String,
  var color: Int, // index
  var isArchived: Boolean = false,
  var productsDetails: List<ProductDetails>,
  var dateCreated: Long = System.currentTimeMillis(),
  var lastEdited: Long = System.currentTimeMillis()
): Searchable {
  override fun doesMatchQuery(query: String): Boolean {
    return name.contains(query, ignoreCase = true)
  }
}
