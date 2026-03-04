package com.ajzia.simplist.screens.products

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ajzia.simplist.model.Product

@Composable
fun ProductGrid(
  products: List<Product>,
  onRemove: (Product) -> Unit
) {
  Column(
    modifier = Modifier.padding(8.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    for (chunk in products.sortedBy { it -> it.name }.chunked(2)) {
      Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceAround
      ) {
        for (product in chunk) {
          Row(
            modifier = Modifier
              .weight(1f)
              .padding(4.dp)
              .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = product.name,
              modifier = Modifier
                .weight(1f),
              maxLines = 3,
              overflow = TextOverflow.Ellipsis,
              style = MaterialTheme.typography.bodyLarge
            )

            if (product.localId != 0) {
              IconButton(
                onClick = { onRemove(product) },
                modifier = Modifier.size(28.dp),
              ) {
                Icon(
                  imageVector = Icons.Default.Clear,
                  contentDescription = "Remove product from list",
                )
              }
            }
          }

          if (chunk.size == 1) {
            Spacer(modifier = Modifier.weight(1f))
          }

        } // for
      }
    } // for
  } // Column
}