package com.ajzia.simplist.screens.components.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.ajzia.simplist.ui.theme.Pink100
import com.ajzia.simplist.ui.theme.checkboxColorMap

@Composable
fun CategoryListLine(
  modifier: Modifier = Modifier,
  name: String,
  color: Color,
  isEnabled: Boolean = true,
  isChecked: Boolean,
  onChecked: () -> Unit,
) {

  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Start
  ) {

    Checkbox(
      checked = isChecked,
      onCheckedChange = { onChecked() },
      enabled = isEnabled,
      colors = CheckboxDefaults.run {
        colors(
          checkedColor = checkboxColorMap[color]!!,
          checkmarkColor = Color.Unspecified,
          uncheckedColor = checkboxColorMap[color]!!
        )
      }
    )

    Text(
      modifier = Modifier.fillMaxWidth(0.55f),
      text = name, maxLines = 1,
      style = MaterialTheme.typography.bodyLarge,
      color = (
          if (isChecked || !isEnabled) Color.Gray
          else Color.Unspecified
          )
    )

  } // Row
}

@Composable
@Preview(showBackground = true)
fun CategoryListLinePreview() {
  CategoryListLine(
    name = "Fruit",
    color = Pink100,
    isChecked = false,
    onChecked = {},
  )
}