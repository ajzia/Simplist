package com.ajzia.simplist.screens.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.ajzia.simplist.nav.NavGraph
import com.ajzia.simplist.ui.theme.AppDrawables

@Composable
fun BottomNavigationBar(
  currentRoute: String,
  onItemClick: (String) -> Unit,
) {

  val items: List<BottomNavItem> = listOf(
    BottomNavItem(
      "Archive",
      ImageVector.vectorResource(AppDrawables.Archive),
      NavGraph.Archive.route
    ),
    BottomNavItem(
      "Lists",
      ImageVector.vectorResource(AppDrawables.Lists),
      NavGraph.Lists.route
    ),
    BottomNavItem(
      "Products",
      ImageVector.vectorResource(AppDrawables.Product),
      NavGraph.Products.route
    )
  )

  NavigationBar(
    containerColor = Color.White,
    tonalElevation = 8.dp
  ) {
    items.forEach { item ->
      NavigationBarItem(
        icon = {
          Icon(imageVector = item.icon,
            contentDescription = item.title,
            modifier = Modifier.size(24.dp)
          )
        },
        onClick = { onItemClick(item.route) },
        selected = currentRoute == item.route,
        label = { Text(item.title) },
        colors = NavigationBarItemDefaults.colors(
          indicatorColor = Color(0x4A9462F6)
        )
      )
    } // items
  } // NavigationBar

} // BottomNavigationBar


data class BottomNavItem(
  val title: String,
  val icon: ImageVector,
  val route: String
)