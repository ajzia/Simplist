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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.ajzia.simplist.model.ProductDetails
import com.ajzia.simplist.ui.theme.Pink100
import com.ajzia.simplist.ui.theme.checkboxColorMap

@Composable
fun ListLine(
  modifier: Modifier = Modifier,
  details: ProductDetails,
  color: Color,
  onChecked: () -> Unit,
  isEnabled: Boolean = true
) {

  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceEvenly
  ) {

    Checkbox(
      checked = details.isChecked,
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
      text = details.name, maxLines = 1,
      style = MaterialTheme.typography.bodyLarge,
      color = (
        if (details.isChecked || !isEnabled) Color.Gray
        else Color.Unspecified
      )
    )

    val quantity = (
      if (details.quantity <= 0.0) ""
      else details.quantity.toString()
    )

    Text(
      modifier = Modifier.fillMaxWidth(0.3f),
      text = quantity,
      style = MaterialTheme.typography.bodyLarge,
      maxLines = 1,
      color = (
        if (details.isChecked || !isEnabled) Color.Gray
        else Color.Unspecified
      ),
      textAlign = TextAlign.Start
    )

  } // Row
}

@Composable
@Preview(showBackground = true)
fun ListLinePreview() {
  val details = ProductDetails("Carrot", 1, false, 10)

  ListLine(
    details = details,
    color = Pink100,
    onChecked = {},
  )
}