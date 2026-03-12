package com.ajzia.simplist.screens.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavController
import com.ajzia.simplist.nav.NavGraph

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultScaffold(
  navController: NavController,
  isProductScreen: Boolean = false,
  onSearch: (String) -> Unit = {},
  onFilter: (String) -> Unit,
  content: @Composable ((PaddingValues) -> Unit)
) {

  val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

  Scaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    topBar = {
      TopAppBar(
        scrollBehavior = scrollBehavior,
        onSearch = { onSearch(it) },
        onFilter = { onFilter(it) },
        isProductScreen = isProductScreen
      )
    },
    floatingActionButton = {
      if (!isProductScreen) {
        FAB { navController.navigate(NavGraph.EditList.createRoute(-1)) }
      }
    },
    bottomBar = {
      BottomNavigationBar(
        currentRoute = navController.currentDestination?.route!!
      ) { route -> navController.navigate(route) }
    }) { paddingValues ->
    content(paddingValues)
  }
}
