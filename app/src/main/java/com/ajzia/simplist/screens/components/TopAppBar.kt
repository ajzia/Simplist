package com.ajzia.simplist.screens.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.ajzia.simplist.ui.theme.CustomPurple
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
  scrollBehavior: TopAppBarScrollBehavior
) {

  var isSearch by remember { mutableStateOf(false) }
  var value by remember { mutableStateOf("") }

  Crossfade(
    modifier = Modifier.animateContentSize(),
    targetState = isSearch,
    label = "Search"
  ) { target ->
    if (!target) {
      TopAppBar(
        title = { },
        actions = { IconButton(Icons.Filled.Search) { isSearch = !isSearch } },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = CustomPurple,
          titleContentColor = Color(0xDAFFFFFF),
          actionIconContentColor = Color(0xDAFFFFFF),
        ),
      )
    } else {
      TextField(
        modifier = Modifier
          .fillMaxWidth()
          .windowInsetsPadding(TopAppBarDefaults.windowInsets),
        placeholder = { Text("Enter list name") },
        value = value,
        onValueChange = { value = it },
        leadingIcon = {
          IconButton(Icons.AutoMirrored.Filled.ArrowBack) {
            isSearch = !isSearch
          }
        },
        trailingIcon = if (value.isNotBlank()) {
          { IconButton(Icons.Filled.Close) { value = "" } }
        } else {
          null
        }
      )
    }
  } // Crossfade
}


@Composable
fun IconButton(imageVector: ImageVector, onClick: () -> Unit) {
  IconButton(onClick = onClick) {
    Icon(
      imageVector = imageVector,
      contentDescription = null,
      modifier = Modifier.size(28.dp)
    )
  }
}