package com.ajzia.simplist.screens.components

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.ajzia.simplist.R

@Composable
fun DropdownMenu(
  isExpanded: Boolean,
  isProductScreen: Boolean,
  onDismissRequest: () -> Unit,
  onFilter: (String) -> Unit,
) {
  val defaultMode = if (isProductScreen) "asc" else "desc"
  val reverseMode = if (isProductScreen) "desc" else "asc"

  var chosenFilter by remember { mutableIntStateOf(0) }
  var chosenMode by remember { mutableStateOf(defaultMode) }

  val listFilters = listOf(
    "Last modified",
    "Date created",
    "Name",
  )

  val productFilters = listOf(
    "Id",
    "Name",
  )

  DropdownMenu(
    expanded = isExpanded,
    onDismissRequest = { onDismissRequest() }
  ) {
    val filters = if (isProductScreen) productFilters
      else listFilters

    for ((i, type) in filters.withIndex()) {
      DropdownMenuItem(
        text = { Text(text = type) },
        leadingIcon = {
          if (chosenFilter != i) return@DropdownMenuItem
          Icon(
            imageVector = ImageVector.vectorResource(
              if (chosenMode == "asc")
                R.drawable.outline_arrow_upward_alt_24
              else R.drawable.outline_arrow_downward_alt_24
            ),
            contentDescription = "Filter mode",
          )
        },
        onClick = {
          chosenMode =
            if (chosenFilter == i && chosenMode == defaultMode)
              reverseMode
            else defaultMode
          chosenFilter = i

          val filter = type
            .replace(" ", "_")
            .plus("_$chosenMode")
            .lowercase()

          onFilter(filter)
        }
      ) // DropdownMenuItem
    }
  }
}

@Composable
@Preview(showBackground = true)
fun DropdownMenuPreview() {
  DropdownMenu(
    isExpanded = true,
    isProductScreen = true,
    onDismissRequest = {},
    onFilter = {}
  )
}