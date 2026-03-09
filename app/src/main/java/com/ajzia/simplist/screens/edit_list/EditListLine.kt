package com.ajzia.simplist.screens.edit_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ajzia.simplist.ui.theme.Pink100
import com.ajzia.simplist.ui.theme.checkboxColorMap

@Composable
fun EditListLine(
  modifier: Modifier = Modifier,
  color: Color,
  onAdd: (String, String) -> Unit,
) {
  var name by remember { mutableStateOf("") }
  var quantity by remember { mutableStateOf("") }

  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceAround
  ) {

    Checkbox(
      checked = false,
      enabled = false,
      onCheckedChange = null,
      colors = CheckboxDefaults.colors(
        checkedColor = checkboxColorMap[color]!!,
        checkmarkColor = Color.Unspecified,
        uncheckedColor = checkboxColorMap[color]!!,
        disabledCheckedColor = checkboxColorMap[color]!!,
        disabledUncheckedColor = checkboxColorMap[color]!!
      )
    )

    TextField(
      textStyle = MaterialTheme.typography.bodyLarge,
      modifier =  Modifier.fillMaxWidth(0.5f),
      value = name,
      onValueChange = { name = it },
      maxLines = 1,
      placeholder = { Text("Product name") },
      colors = TextFieldDefaults.colors(
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
        focusedIndicatorColor = Color.Unspecified,
        unfocusedIndicatorColor = Color.Unspecified,
        disabledIndicatorColor = Color.Transparent,
      ),
      keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Next
      ),
    )

    TextField(
      textStyle = MaterialTheme.typography.bodyLarge,
      modifier = Modifier.fillMaxWidth(0.45f),
      value = quantity,
      onValueChange = { quantity = it },
      placeholder = { Text("0") },
      colors = TextFieldDefaults.colors(
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
        focusedIndicatorColor = Color.Unspecified,
        unfocusedIndicatorColor = Color.Unspecified,
        disabledIndicatorColor = Color.Unspecified,
      ),
      keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Number,
        imeAction = ImeAction.Done
      ),
    ) // TextField

    IconButton(
      onClick = {
        onAdd(name, quantity)
        name = ""
        quantity = ""
      },
      modifier = Modifier.padding(start = 8.dp),
    ) {
      Icon(
        imageVector = Icons.Default.Add,
        contentDescription = "Add product to the list",
      )
    } // IconButton

  } // Row
}


@Composable
fun EditListLine(
  modifier: Modifier = Modifier,
  name: String,
  quantity: Int,
  isChecked: Boolean,
  color: Color,
  onRemove: () -> Unit,
) {
  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceAround
  ) {

    Checkbox(
      checked = isChecked,
      enabled = true,
      onCheckedChange = null,
      colors = CheckboxDefaults.colors(
        checkedColor = checkboxColorMap[color]!!,
        checkmarkColor = Color.Unspecified,
        uncheckedColor = checkboxColorMap[color]!!,
        disabledCheckedColor = checkboxColorMap[color]!!,
        disabledUncheckedColor = checkboxColorMap[color]!!
      )
    )

    Text(
      style = MaterialTheme.typography.bodyLarge,
      modifier = Modifier.fillMaxWidth(0.5f)
        .padding(start = 12.dp),
      text = name,
      maxLines = 1,
    )

    Text(
      style = MaterialTheme.typography.bodyLarge,
      modifier = Modifier.fillMaxWidth(0.45f),
      text = (
        if (quantity == 0) ""
        else quantity.toString()
      ),
      maxLines = 1,
      textAlign = TextAlign.End
    ) // TextField

    IconButton(
      onClick = { onRemove() },
      modifier = Modifier.padding(start = 8.dp),
    ) {
      Icon(
        imageVector = Icons.Default.Clear,
        contentDescription = "Remove product from list",
      )
    } // IconButton

  } // Row
}



@Composable
@Preview(showBackground = true)
fun AddListLinePreview() {
  EditListLine(
    name = "Carrot",
    quantity = 10,
    isChecked = false,
    color = Pink100,
    onRemove = {}
  )
}