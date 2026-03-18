package com.ajzia.simplist.screens.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.ajzia.simplist.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
  scrollBehavior: TopAppBarScrollBehavior,
  onSearch: (String) -> Unit,
  onFilter: (String) -> Unit,
  isProductScreen: Boolean
) {

  var isSearch by remember { mutableStateOf(false) }
  var isExpanded by remember { mutableStateOf(false) }
  var query by remember { mutableStateOf("") }

  TopAppBar(
    title = { },
    actions = {
      if (!isProductScreen) {
        SearchField(
          modifier = Modifier
            .animateContentSize(
              animationSpec = spring(
                stiffness = Spring.StiffnessLow
              )
            )
            .fillMaxWidth(if (isSearch) 1f else 0.8f)
            .padding(4.dp),
          isSearch = isSearch,
          onClick = { isSearch = true },
          query = query,
          onSearch = {
            query = it
            onSearch(query)
          },
          onClear = {
            query = ""
            onSearch(query)
          },
          onBack = {
            isSearch = false
            query = ""
            onSearch(query)
          }
        )
      }

      AnimatedVisibility(
        visible = !isSearch,
        enter = fadeIn() + expandHorizontally(),
        exit = fadeOut() + shrinkHorizontally()
      ) {
        Box {
          IconButton(onClick = { isExpanded = !isExpanded }) {
            Icon(
              imageVector = ImageVector.vectorResource(R.drawable.outline_filter_alt_24),
              contentDescription = "Filter contents",
              modifier = Modifier.size(40.dp)
            )
          }

          DropdownMenu(
            isExpanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            onFilter = { onFilter(it) },
            isProductScreen = isProductScreen
          )
        }
      }
    }, // actions
    scrollBehavior = scrollBehavior,
    colors = TopAppBarDefaults.topAppBarColors(
      containerColor = CustomPurple,
      scrolledContainerColor = CustomPurple,
      actionIconContentColor = Color(0xDAFFFFFF),
    ),
  )
}