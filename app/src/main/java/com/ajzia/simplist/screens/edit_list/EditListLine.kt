package com.ajzia.simplist.screens.edit_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ajzia.simplist.core.util.TestTags
import com.ajzia.simplist.model.Product
import com.ajzia.simplist.ui.theme.AppIcons
import com.ajzia.simplist.ui.theme.SimplistTheme

@Composable
fun EditListLine(
  modifier: Modifier = Modifier,
  name: String = "",
  prodQuantity: Int = -1,
  isChecked: Boolean = false,
  colorIdx: Int,
  products: List<Product>,
  index: String = "",
  quantityTag: String = TestTags.QUANTITY_FIELD,
  onRemove: () -> Unit = {},
  onQuantityChange: (String) -> Unit = {},
  onAdd: (String, String) -> Unit = {_,_ -> },
) {
  val nameState = rememberTextFieldState(name)
  var quantity by remember(name) { mutableStateOf(
    if (prodQuantity != -1) prodQuantity.toString() else "") }

  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceAround
  ) {

    val checkBoxColor = SimplistTheme.cardColorList[colorIdx].checkboxColor

    Checkbox(
      checked = isChecked,
      enabled = false,
      onCheckedChange = null,
      colors = CheckboxDefaults.colors(
        checkedColor = checkBoxColor,
        checkmarkColor = Color.Unspecified,
        uncheckedColor = checkBoxColor,
        disabledCheckedColor = checkBoxColor,
        disabledUncheckedColor = checkBoxColor
      )
    )

    if (name.isBlank()) {
      AutoCompleteTextField(
        modifier = Modifier
          .fillMaxWidth(0.5f)
          .testTag(TestTags.PRODUCT_FIELD),
        value = nameState.text.toString(),
        onValueChange = { nameState.setTextAndPlaceCursorAtEnd(it) },
        dropdownColor = SimplistTheme.cardColorList[colorIdx].cardColor,
        products = products,
        onSuggestionSelected = { nameState.setTextAndPlaceCursorAtEnd(it) }
      )
    } else {
      Text( // name
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier
          .fillMaxWidth(0.5f)
          .padding(start = 12.dp),
        text = name,
        maxLines = 1,
        color = MaterialTheme.colorScheme.onSurface
      )
    }

    TextField( // quantity
      textStyle = MaterialTheme.typography.bodyLarge,
      modifier = Modifier
        .fillMaxWidth(0.45f)
        .testTag("$quantityTag$index"),
      value = quantity,
      onValueChange = {
        quantity = it
        onQuantityChange(quantity) },
      placeholder = { Text("0") },
      colors = TextFieldDefaults.colors(
        focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
        unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
        focusedTextColor = MaterialTheme.colorScheme.onSurface,
        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
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
    )

    IconButton(
      onClick = {
        if (name.isBlank()) {
          onAdd(
            nameState.text.toString().lowercase().trim(),
            quantity
          )
          nameState.setTextAndPlaceCursorAtEnd("")
          quantity = ""
        } else {
          onRemove()
        }
      },
      modifier = Modifier
        .padding(start = 8.dp)
        .testTag(TestTags.ADD_REMOVE + " $index"),
    ) {
      Icon(
        imageVector = (
          if (name.isBlank()) AppIcons.Add
          else AppIcons.Clear
        ),
        contentDescription = (
          if (name.isBlank()) "Add product to the list"
          else "Remove product from the list"
        ),
        tint = checkBoxColor
      )
    } // IconButton

  } // Row
}

@Composable
@Preview(showBackground = true)
fun AddListLinePreview() {
  EditListLine(
    name = "Carrot",
    prodQuantity = 10,
    isChecked = false,
    colorIdx = 2,
    products = emptyList(),
  )
}