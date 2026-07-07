package com.ajzia.simplist.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ajzia.simplist.ui.theme.AppDrawables
import com.ajzia.simplist.ui.theme.AppIcons

@Composable
fun SearchField(
  modifier: Modifier = Modifier,
  isSearch: Boolean,
  query: String,
  onClick: () -> Unit,
  onSearch: (String) -> Unit,
  onClear: () -> Unit,
  onBack: () -> Unit,
) {
  TextField(
    modifier = modifier
      .clip(CircleShape)
      .background(
        color = MaterialTheme.colorScheme.surface
      ).fillMaxWidth(0.9f)
      .clickable { if (!isSearch) onClick() },
    textStyle = MaterialTheme.typography.bodyLarge,
    value = query,
    enabled = isSearch,
    onValueChange = { onSearch(it) },
    placeholder = { Text(text = "Search") },
    colors = TextFieldDefaults.colors(
      focusedTextColor = MaterialTheme.colorScheme.onSurface,
      unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
      disabledTextColor = MaterialTheme.colorScheme.onSurface,
      disabledPlaceholderColor = MaterialTheme.colorScheme.onSurface,
      unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface,
      focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface,
      focusedContainerColor = Color.Transparent,
      unfocusedContainerColor = Color.Transparent,
      disabledContainerColor = Color.Transparent,
      focusedIndicatorColor = Color.Transparent,
      unfocusedIndicatorColor = Color.Transparent,
      disabledIndicatorColor = Color.Transparent,
    ),
    keyboardOptions = KeyboardOptions(
      keyboardType = KeyboardType.Text,
      imeAction = ImeAction.Done
    ),
    leadingIcon = {
      if (!isSearch) {
        Icon(
          imageVector = AppIcons.Search,
          contentDescription = null,
          tint = MaterialTheme.colorScheme.onSurface,
          modifier = Modifier.size(28.dp)
        )
      } else {
        IconButton(
          imageVector = ImageVector.vectorResource(AppDrawables.Back),
          onClick = { onBack() },
          tint = MaterialTheme.colorScheme.onSurface,
          contentDescription = "Exit search"
        )
      }
    }, // leadingIcon
    trailingIcon = {
      if (query == "" || !isSearch) return@TextField
      IconButton(
        imageVector = AppIcons.Clear,
        onClick = { onClear() },
        tint = MaterialTheme.colorScheme.onSurface,
        contentDescription = "Clear search"
      )
    } // trailingIcon
  )
}
