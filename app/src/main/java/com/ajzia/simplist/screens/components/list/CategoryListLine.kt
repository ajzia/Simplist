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
import com.ajzia.simplist.ui.theme.SimplistTheme

@Composable
fun CategoryListLine(
  modifier: Modifier = Modifier,
  name: String,
  colorIdx: Int,
  isEnabled: Boolean = true,
  isChecked: Boolean,
  onChecked: () -> Unit,
) {

  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Start
  ) {

    val checkBoxColor = SimplistTheme.cardColorList[colorIdx].checkboxColor

    Checkbox(
      checked = isChecked,
      onCheckedChange = { onChecked() },
      enabled = isEnabled,
      colors = CheckboxDefaults.run {
        colors(
          checkedColor = checkBoxColor,
          checkmarkColor = Color.Unspecified,
          uncheckedColor = checkBoxColor
        )
      }
    )

    Text(
      modifier = Modifier.fillMaxWidth(0.55f),
      text = name, maxLines = 1,
      style = MaterialTheme.typography.bodyLarge,
      color = (
          if (isChecked || !isEnabled)
            MaterialTheme.colorScheme.onSurfaceVariant
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
    colorIdx = 1,
    isChecked = false,
    onChecked = {},
  )
}