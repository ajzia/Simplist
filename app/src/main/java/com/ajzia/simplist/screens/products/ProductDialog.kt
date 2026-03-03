package com.ajzia.simplist.screens.products

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun ProductDialog(
  productName: String,
  onNameChange: (String) -> Unit,
  categoryName: String,
  onDismissRequest: () -> Unit,
  onAdd: () -> Unit
) {
  Dialog(
    onDismissRequest = { onDismissRequest() }
  ) {
    Card(
      shape = RoundedCornerShape(12.dp),
    ) {
      Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Text(
          text = "Add product to the $categoryName category:",
          style = MaterialTheme.typography.bodyLarge,
        )

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceAround,
          verticalAlignment = Alignment.CenterVertically
        ) {
          TextField(
            modifier = Modifier.fillMaxWidth(0.8f),
            textStyle = MaterialTheme.typography.bodyMedium,
            value = productName,
            onValueChange = { onNameChange(it) },
            placeholder = { Text("Product name") },
            colors = TextFieldDefaults.colors(
              focusedContainerColor = Color.Transparent,
              unfocusedContainerColor = Color.Transparent,
              disabledContainerColor = Color.Transparent,
              focusedIndicatorColor = Color.Unspecified,
              unfocusedIndicatorColor = Color.Unspecified,
              disabledIndicatorColor = Color.Unspecified,
            ),
            keyboardOptions = KeyboardOptions(
              keyboardType = KeyboardType.Decimal,
              imeAction = ImeAction.Done
            ),
          ) // TextField

          IconButton(
            onClick = { onAdd() },
            modifier = Modifier.size(28.dp),
          ) {
            Icon(
              imageVector = Icons.Default.Add,
              contentDescription = "Add product to the category",
            )
          } // IconButton
        }

      } // Column

    } // Card
  } // Dialog
}
