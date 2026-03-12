package com.ajzia.simplist.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "products")
@Serializable
data class Product(
  @PrimaryKey(autoGenerate = true)
  val localId: Int = 0,
  val remoteId: String = "",
  val name: String,
  val categoryId: Int,
): Searchable {
  override fun doesMatchQuery(query: String): Boolean {
    return name.contains(query, ignoreCase = true)
  }
}
