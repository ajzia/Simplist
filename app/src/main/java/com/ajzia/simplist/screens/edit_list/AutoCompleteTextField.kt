package com.ajzia.simplist.screens.edit_list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.ajzia.simplist.model.Product
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutoCompleteTextField(
  modifier: Modifier = Modifier,
  value: String,
  dropdownColor: Color,
  onValueChange: (String) -> Unit,
  products: List<Product>,
  onSuggestionSelected: (String) -> Unit
) {
  var isExpanded by remember { mutableStateOf(false) }
  var filteredSuggestions by remember {
    mutableStateOf(emptyList<Product>()) }
  var debounceJob by remember { mutableStateOf<Job?>(null) }

  LaunchedEffect(value) {
    debounceJob?.cancel()
    debounceJob = launch {
      delay(100.milliseconds)
      filteredSuggestions = products.filter {
        value.isNotEmpty() && it.name.contains(value, ignoreCase = true)
      }
    }
  }

  ExposedDropdownMenuBox(
    expanded = isExpanded,
    onExpandedChange = { isExpanded = it },
  ) {
    TextField(
      modifier = modifier,
      textStyle = MaterialTheme.typography.bodyLarge,
      value = value,
      onValueChange = {
        onValueChange(it)
        isExpanded = it.isNotEmpty()
      },
      maxLines = 1,
      placeholder = { Text("Product name") },
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
        disabledIndicatorColor = Color.Transparent,
      ),
      keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Next
      ),
    )

    if (filteredSuggestions.isNotEmpty()) {
      DropdownMenu(
        modifier = Modifier.exposedDropdownSize(),
        expanded = isExpanded,
        containerColor = dropdownColor,
        border = BorderStroke(1.dp, Color.Gray),
        onDismissRequest = { isExpanded = false },
        properties = PopupProperties(focusable = false)
      ) {
        filteredSuggestions.forEach { suggestion ->
          DropdownMenuItem(
            text = { Text(suggestion.name) },
            onClick = {
              onSuggestionSelected(suggestion.name)
              onValueChange(suggestion.name)
              isExpanded = false
            },
            colors = MenuItemColors(
              textColor = MaterialTheme.colorScheme.onSurface,
              disabledTextColor = MaterialTheme.colorScheme.onSurface,
              leadingIconColor = Color.Transparent,
              trailingIconColor = Color.Transparent,
              disabledLeadingIconColor = Color.Transparent,
              disabledTrailingIconColor = Color.Transparent
            ),
            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
          )
        }
      }
    }
  }
}