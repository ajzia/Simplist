package com.ajzia.simplist.screens.products

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.ajzia.simplist.core.util.TestTags
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
    modifier = modifier.border(
      width = 1.dp,
      color = MaterialTheme.colorScheme.outline,
      shape = RoundedCornerShape(12.dp)
    ),
    shape = RoundedCornerShape(12.dp),
    elevation = CardDefaults.cardElevation(8.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.secondary,
      contentColor = MaterialTheme.colorScheme.onSecondary
    )
  ) {
    CategoryHeadline(
      name = name,
      isVisible = isVisible,
      onCategoryClick = { isVisible = !isVisible },
      onAdd = onAdd
    )

    if (isVisible) {
      if (products.isNotEmpty()) {
        ProductGrid(
          modifier = Modifier.testTag(TestTags.PRODUCT_GRID + " $name"),
          products = products,
          onRemove = { onRemove(it) }
        )
      }
    }
  }

}
