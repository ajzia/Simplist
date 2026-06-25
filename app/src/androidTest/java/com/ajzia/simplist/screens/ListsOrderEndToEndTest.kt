package com.ajzia.simplist.screens

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ajzia.simplist.MainActivity
import com.ajzia.simplist.core.util.TestTags
import com.ajzia.simplist.di.AppModule
import com.ajzia.simplist.nav.NavGraph
import com.ajzia.simplist.screens.archive.ArchiveScreen
import com.ajzia.simplist.screens.edit_list.EditListScreen
import com.ajzia.simplist.screens.lists.ListsScreen
import com.ajzia.simplist.screens.products.ProductsScreen
import com.ajzia.simplist.ui.theme.SimplistTheme
import com.ajzia.simplist.ui.theme.colors
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class ListsOrderEndToEndTest {

  @get:Rule(order = 0)
  val hiltRule = HiltAndroidRule(this)

  @get:Rule(order = 1)
  val composeRule = createAndroidComposeRule<MainActivity>()

  @Before
  fun setUp() {
    hiltRule.inject()
    composeRule.activity.setContent {
      val navController = rememberNavController()
      SimplistTheme {
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
              listId = id,
              onBack = {
                navController.popBackStack()
              }
            )
          }
        } // NavHost
      } // SimplistTheme
    } // setContent
  }

  @Test
  fun filterProductsLists_differentOrders() {
    // Add 3 lists
    for (i in 1..3) {
      composeRule.onNodeWithContentDescription("Create new list").performClick()
      // Change color
      val randomColor: Int = colors.indices.random()
      composeRule.onNodeWithTag(TestTags.COLOR_LIST)
        .performScrollToIndex(randomColor)
      composeRule.onNodeWithTag(TestTags.COLOR + " $randomColor")
        .performClick()
      // Add title and product
      composeRule.onNodeWithTag(TestTags.TITLE_FIELD).performTextInput("$i")
      composeRule.onNodeWithTag(TestTags.PRODUCT_FIELD).performTextInput("$i")
      composeRule.onNodeWithTag(TestTags.QUANTITY_FIELD).performTextInput("$i")
      composeRule.onNodeWithContentDescription(
        "Add product to the list").performClick()
      composeRule.onNodeWithContentDescription("Submit list").performClick()
    }

    // Choose name descending filter
    composeRule.onNodeWithContentDescription("Filter contents").performClick()
    composeRule.onNodeWithText("Name").performClick()
    composeRule.waitForIdle()

    // Assert lists are in the title descending order
    for (i in 0..2) {
      composeRule.onNodeWithTag(TestTags.LIST_TITLE + " $i")
        .assertTextEquals("${3-i}")
    }

    // Choose date created ascending filter
    composeRule.onNodeWithText("Date created").performClick()
    composeRule.onNodeWithText("Date created").performClick()
    composeRule.waitForIdle()

    // Assert lists are in the date created ascending order
    for (i in 0..2) {
      composeRule.onNodeWithTag(TestTags.LIST_TITLE + " $i")
        .assertTextEquals("${i+1}")
    }

    // Edit the fist list
    composeRule.onNodeWithContentDescription("Edit list 0").performClick()
    composeRule.onNodeWithTag(TestTags.TITLE_FIELD)
      .assertTextEquals("1")
    composeRule.onNodeWithTag(TestTags.CHANGEABLE_QUANTITY_FIELD + "0") // index of the 1st
      .performTextReplacement("5")
    composeRule.onNodeWithContentDescription("Submit list").performClick()

    // Choose last modified descending filter
    composeRule.onNodeWithContentDescription("Filter contents").performClick()
    composeRule.onNodeWithText("Last modified").performClick()
    composeRule.waitForIdle()

    // Assert lists are in the last modified descending order
    composeRule.onNodeWithTag(TestTags.LIST_TITLE + " 0")
      .assertTextEquals("1")
    composeRule.onNodeWithTag(TestTags.LIST_TITLE + " 1")
      .assertTextEquals("3")
    composeRule.onNodeWithTag(TestTags.LIST_TITLE + " 2")
      .assertTextEquals("2")
  }
}
