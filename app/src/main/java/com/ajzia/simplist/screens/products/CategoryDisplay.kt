package com.ajzia.simplist.screens.products

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ajzia.simplist.model.Product

@Composable
fun CategoryDisplay(
  modifier: Modifier = Modifier,
  name: String,
  products: List<Product>,
  onAdd: () -> Unit,
  onRemove: (Product) -> Unit
) {
  var isVisible by remember { mutableStateOf(false) }

  Card(
    modifier = modifier,
    shape = RoundedCornerShape(8.dp),
    elevation = CardDefaults.cardElevation(8.dp),
  ) {
    CategoryHeadline(
      name = name,
      isVisible = isVisible,
      onCategoryClick = {
        isVisible = !isVisible
      },
      onAdd = onAdd
    )

    if (isVisible && products.isNotEmpty()) {
      ProductGrid(
        products = products,
        onRemove = { onRemove(it) }
      )
    }
  }

}
