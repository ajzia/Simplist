package com.ajzia.simplist.screens.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun IconButton(
  imageVector: ImageVector,
  onClick: () -> Unit,
  tint: Color = LocalContentColor.current,
  contentDescription: String? = null) {
  IconButton(onClick = onClick) {
    Icon(
      imageVector = imageVector,
      contentDescription = contentDescription,
      modifier = Modifier.size(28.dp),
      tint = tint
    )
  }
}