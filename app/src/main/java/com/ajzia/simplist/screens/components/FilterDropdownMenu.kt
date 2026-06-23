package com.ajzia.simplist.screens.components

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.vectorResource
import com.ajzia.simplist.R
import com.ajzia.simplist.core.util.TestTags
import com.ajzia.simplist.model.sorting.SortOption
import com.ajzia.simplist.model.sorting.SortingMode

@Composable
fun <T : Enum<T>> FilterDropdownMenu(
  isExpanded: Boolean,
  defaultMode: SortingMode,
  filters: Array<T>,
  sortOption: SortOption<T>,
  onDismissRequest: () -> Unit,
  onFilter: (String) -> Unit,
) {
  DropdownMenu(
    modifier = Modifier.testTag(TestTags.FILTER_SECTION),
    expanded = isExpanded,
    onDismissRequest = { onDismissRequest() }
  ) {
    for (filterType in filters) {
      val typeName = filterType.name
        .lowercase()
        .replace("_", " ")
        .replaceFirstChar { c -> c.uppercase() }
      DropdownMenuItem(
        text = { Text(text = typeName) },
        leadingIcon = {
          if (sortOption.filter == filterType) {
            Icon(
              imageVector = ImageVector.vectorResource(
                if (sortOption.mode == SortingMode.ASC)
                  R.drawable.outline_arrow_upward_alt_24
                else R.drawable.outline_arrow_downward_alt_24
              ),
              contentDescription = "Filter mode",
            )
          }
        },
        onClick = {
          val mode = (
            if (sortOption.filter == filterType && sortOption.mode == defaultMode)
              defaultMode.reverse()
            else defaultMode
          )

          val newOption = SortOption(filterType, mode)
          onFilter(newOption.filterName)
        }
      ) // DropdownMenuItem
    }
  }
}