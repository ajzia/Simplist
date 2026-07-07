package com.ajzia.simplist.screens.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun FAB(
  imageVector: ImageVector,
  contentDescription: String,
  onClick: () -> Unit,
) {
  FloatingActionButton(
    onClick = { onClick() },
    containerColor = MaterialTheme.colorScheme.tertiary,
    contentColor = MaterialTheme.colorScheme.onTertiary
  ) {
    Icon(
      modifier = Modifier.size(28.dp),
      imageVector = imageVector,
      contentDescription = contentDescription
    )
  }
}