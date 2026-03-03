package com.ajzia.simplist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ajzia.simplist.nav.NavGraph
import com.ajzia.simplist.screens.archive.ArchiveScreen
import com.ajzia.simplist.screens.lists.ListsScreen
import com.ajzia.simplist.screens.edit_list.EditListScreen
import com.ajzia.simplist.screens.products.ProductsScreen
import com.ajzia.simplist.ui.theme.SimplistTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      SimplistTheme {

//        val clipboard = ContextCompat.getSystemService(
//          this,
//          ClipboardManager::class.java
//        )

        val navController = rememberNavController()

        NavHost(
          navController = navController,
          startDestination = NavGraph.Lists.route
        ) {
          composable(NavGraph.Lists.route) {
            ListsScreen(
              navController = navController
            )
          }

          composable(NavGraph.Archive.route) {
            ArchiveScreen(
              navController = navController
            )
          }

          composable(NavGraph.Products.route) {
            ProductsScreen(
              navController = navController,
              context = applicationContext
            )
          }

          composable(
            NavGraph.EditList.route,
            arguments = listOf(
              navArgument("id") {
                type = NavType.IntType
              }
            )
          ) {
            val id: Int = it.arguments?.getInt("id")!!

            EditListScreen(
              context = applicationContext,
              listId = id,
              onBack = { navController.navigate(NavGraph.Lists.route) }
            )

          }
        } // NavHost

      }
    }
  }
}
