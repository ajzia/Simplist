package com.ajzia.simplist.screens.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun FAB(
  containerColor: Color,
  imageVector: ImageVector,
  contentDescription: String,
  onClick: () -> Unit,
) {
  FloatingActionButton(
    onClick = { onClick() },
    containerColor = containerColor,
    contentColor = Color(0xDAFFFFFF)
  ) {
    Icon(
      modifier = Modifier.size(28.dp),
      imageVector = imageVector,
      contentDescription = contentDescription
    )
  }
}