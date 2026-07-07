package com.ajzia.simplist.screens.products

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ajzia.simplist.screens.components.IconButton
import com.ajzia.simplist.ui.theme.AppDrawables
import com.ajzia.simplist.ui.theme.AppIcons

@Composable
fun CategoryHeadline(
  name: String,
  isVisible: Boolean,
  onCategoryClick: () -> Unit,
  onAdd: () -> Unit,
) {
  Row(
    modifier = Modifier.fillMaxWidth().padding(8.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      style = MaterialTheme.typography.headlineSmall,
      modifier = Modifier.padding(horizontal = 12.dp),
      text = name,
    )

    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
      IconButton(
        imageVector = AppIcons.Add,
        onClick = { onAdd() },
        contentDescription = "Add to $name"
      )

      IconButton(
        imageVector = (
            if (isVisible) ImageVector.vectorResource(AppDrawables.ArrowDropUp)
            else ImageVector.vectorResource(AppDrawables.ArrowDropDown)
            ),
        onClick = { onCategoryClick() },
        contentDescription = "Show/hide products $name"
      )
    }

  } // Row
}

@Composable
@Preview(showBackground = true)
fun CategoryHeadlinePreview() {
  CategoryHeadline(
    name = "Fruit",
    isVisible = true,
    {},
    {}
  )
}
