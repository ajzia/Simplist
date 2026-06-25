package com.ajzia.simplist.screens

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
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
class ListsEndToEndTest {

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
  fun createAndSaveProductsList_editAndSaveAfterwards() {
    composeRule.onNodeWithContentDescription("Create new list").performClick()

    // Change color
    val randomColor: Int = colors.indices.random()
    composeRule.onNodeWithTag(TestTags.COLOR_LIST)
      .performScrollToIndex(randomColor)
    composeRule.onNodeWithTag(TestTags.COLOR + " $randomColor")
      .performClick()
    // Add title and products
    composeRule.onNodeWithTag(TestTags.TITLE_FIELD).performTextInput("title")
    for (i in 1..3) {
      composeRule.onNodeWithTag(TestTags.PRODUCT_FIELD).performTextInput("$i")
      composeRule.onNodeWithTag(TestTags.QUANTITY_FIELD).performTextInput("$i")
      composeRule.onNodeWithContentDescription(
        "Add product to the list").performClick()
    }
    composeRule.onNodeWithContentDescription("Submit list").performClick()

    // Assert list is displayed and click to edit
    composeRule.onNodeWithTag(TestTags.SAVED_LIST + " 0")
      .assertIsDisplayed()
    composeRule.onNodeWithTag(TestTags.LIST_TITLE + " 0")
      .assertTextEquals("title")
    composeRule.onNodeWithContentDescription("Edit list 0").performClick()

    // Assert the chosen list is the one edited
    composeRule.onNodeWithTag(TestTags.TITLE_FIELD)
      .assertTextEquals("title")
    composeRule.onNodeWithTag(TestTags.COLOR_LIST)
      .performScrollToIndex(randomColor)
    composeRule.onNodeWithTag(TestTags.COLOR + " $randomColor")
      .assertExists()
      .assertIsSelected()
    // Remove product 2, change quantity of product 1 and list title, save
    composeRule.onNodeWithTag(TestTags.ADD_REMOVE + " 1") // 1 is the index of the 2nd product
      .performClick()
    composeRule.waitForIdle()
    composeRule.onNodeWithTag(TestTags.CHANGEABLE_QUANTITY_FIELD + "0") // index of the 1st
      .performTextReplacement("6")
    composeRule.waitForIdle()
    composeRule.onNodeWithTag(TestTags.TITLE_FIELD)
      .performTextReplacement("new title")
    composeRule.onNodeWithContentDescription("Submit list").performClick()

    // Assert list and products are displayed
    composeRule.onNodeWithTag(TestTags.SAVED_LIST + " 0")
      .assertIsDisplayed()
    composeRule.onNodeWithTag(TestTags.LIST_TITLE + " 0")
      .assertTextEquals("new title")
    composeRule.onNodeWithText("1").isDisplayed()
    composeRule.onNodeWithText("6").isDisplayed()
    composeRule.onAllNodesWithText("3").assertCountEquals(2)
  }
}