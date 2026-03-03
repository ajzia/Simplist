package com.ajzia.simplist.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "products")
@Serializable
data class Product(
  @PrimaryKey(autoGenerate = true)
  val id: Int = 0,
  val name: String,
  val categoryId: String,
  val isLocal: Boolean
)

