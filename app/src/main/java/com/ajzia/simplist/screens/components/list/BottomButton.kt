package com.ajzia.simplist.screens.components.list

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun BottomButton(
  icon: ImageVector,
  onClick: () -> Unit,
  description: String,
) {
  IconButton(
    onClick = { onClick() }
  ) {
    Icon(
      modifier = Modifier.size(28.dp),
      imageVector = icon,
      contentDescription = description,
      tint = Color.DarkGray
    )
  }
}