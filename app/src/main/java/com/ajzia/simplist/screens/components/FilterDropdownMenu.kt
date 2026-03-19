package com.ajzia.simplist.screens.components

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.ajzia.simplist.R
import com.ajzia.simplist.model.CategoryFilter
import com.ajzia.simplist.model.ListFilter
import com.ajzia.simplist.model.SortingMode

@Composable
fun FilterDropdownMenu(
  isExpanded: Boolean,
  isProductScreen: Boolean,
  onDismissRequest: () -> Unit,
  onFilter: (String) -> Unit,
) {
  val filters = if (isProductScreen) CategoryFilter.entries else ListFilter.entries

  val defaultMode = if (isProductScreen) SortingMode.ASC else SortingMode.DESC
  val reverseMode = if (isProductScreen) SortingMode.DESC else SortingMode.ASC

  var chosenFilter by remember { mutableStateOf(filters.first()) }
  var chosenMode by remember { mutableStateOf(defaultMode) }

  DropdownMenu(
    expanded = isExpanded,
    onDismissRequest = { onDismissRequest() }
  ) {

    for (type in filters) {
      val typeName = type.name
        .lowercase()
        .replace("_", " ")
        .replaceFirstChar { c -> c.uppercase() }
      DropdownMenuItem(
        text = { Text(text = typeName) },
        leadingIcon = {
          if (chosenFilter != type) return@DropdownMenuItem
          Icon(
            imageVector = ImageVector.vectorResource(
              if (chosenMode == SortingMode.ASC)
                R.drawable.outline_arrow_upward_alt_24
              else R.drawable.outline_arrow_downward_alt_24
            ),
            contentDescription = "Filter mode",
          )
        },
        onClick = {
          chosenMode =
            if (chosenFilter == type && chosenMode == defaultMode)
              reverseMode
            else defaultMode
          chosenFilter = type

          val filter = type.name.lowercase()
            .plus("_${chosenMode.name.lowercase()}")

          onFilter(filter)
        }
      ) // DropdownMenuItem
    }
  }
}

@Composable
@Preview(showBackground = true)
fun DropdownMenuPreview() {
  FilterDropdownMenu(
    isExpanded = true,
    isProductScreen = false,
    onDismissRequest = {},
    onFilter = {}
  )
}