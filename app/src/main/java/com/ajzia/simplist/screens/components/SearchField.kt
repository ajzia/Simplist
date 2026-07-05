package com.ajzia.simplist.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ajzia.simplist.ui.theme.AppDrawables
import com.ajzia.simplist.ui.theme.AppIcons
import com.ajzia.simplist.ui.theme.DimmedCustomPurple

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
      .clip(if (!isSearch) CircleShape else RectangleShape)
      .background(
        color = (
          if (!isSearch) DimmedCustomPurple
          else Color.Transparent
        )
      ).fillMaxWidth(0.9f)
      .border(1.dp, Color.Gray, CircleShape)
      .clickable { if (!isSearch) onClick() },
    textStyle = MaterialTheme.typography.bodyLarge,
    value = query,
    enabled = isSearch,
    onValueChange = { onSearch(it) },
    placeholder = { Text(text = "Search") },
    colors = TextFieldDefaults.colors(
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
          modifier = Modifier.size(28.dp)
        )
      } else {
        IconButton(
          imageVector = ImageVector.vectorResource(AppDrawables.Back),
          onClick = { onBack() },
          contentDescription = "Exit search"
        )
      }
    }, // leadingIcon
    trailingIcon = {
      if (query == "" || !isSearch) return@TextField
      IconButton(
        imageVector = AppIcons.Clear,
        onClick = { onClear() },
        contentDescription = "Clear search"
      )
    } // trailingIcon
  )
}

@Composable
fun IconButton(
  imageVector: ImageVector,
  onClick: () -> Unit,
  contentDescription: String? = null) {
  IconButton(onClick = onClick) {
    Icon(
      imageVector = imageVector,
      contentDescription = contentDescription,
      modifier = Modifier.size(28.dp)
    )
  }
}