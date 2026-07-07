package com.ajzia.simplist.screens.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import com.ajzia.simplist.nav.NavGraph
import com.ajzia.simplist.ui.theme.AppDrawables
import com.ajzia.simplist.ui.theme.AppIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultScaffold(
  navController: NavController,
  chosenFilter: String,
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
        isProductScreen = isProductScreen,
        chosenFilter = chosenFilter,
      )
    },
    floatingActionButton = {
      if (!isProductScreen) {
        FAB(
          imageVector = AppIcons.Add,
          contentDescription = "Create new list",
          onClick = {
            navController.navigate(NavGraph.EditList.createRoute(-1))
          }
        )
      }
    },
    bottomBar = {
      BottomNavigationBar(
        currentRoute = navController.currentDestination?.route!!
      ) { route ->
        navController.navigate(route) {
          popUpTo(NavGraph.Lists.route) { inclusive = true }
        }
      }
    }) { paddingValues ->
    content(paddingValues)
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultScaffold(
  onBack: () -> Unit,
  onSubmit: () -> Unit,
  content: @Composable ((PaddingValues) -> Unit)
) {
  Scaffold(
    topBar = {
      TopAppBar(
        title = { },
        navigationIcon = {
          IconButton(
            imageVector = ImageVector.vectorResource(AppDrawables.Back),
            onClick = { onBack() }
          )
        },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = MaterialTheme.colorScheme.primary,
          actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
        )
      )
    },
    floatingActionButton = {
      FAB(
        imageVector = AppIcons.Submit,
        contentDescription = "Submit list",
        onClick = { onSubmit() }
      )
    }) { paddingValues ->
    content(paddingValues)
  }
}