package com.ajzia.simplist.screens.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ajzia.simplist.ui.theme.CustomPurple

@Composable
fun FAB(
  onClick: () -> Unit,
) {
  FloatingActionButton(
    onClick = { onClick() },
    containerColor = CustomPurple,
    contentColor = Color(0xDAFFFFFF)
  ) {
    Icon(
      modifier = Modifier.size(28.dp),
      imageVector = Icons.Default.Add,
      contentDescription = "Create new list"
    )
  }
}