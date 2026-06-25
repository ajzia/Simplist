package com.ajzia.simplist.screens.products

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ajzia.simplist.MainActivity
import com.ajzia.simplist.core.util.TestTags
import com.ajzia.simplist.di.AppModule
import com.ajzia.simplist.nav.NavGraph
import com.ajzia.simplist.ui.theme.SimplistTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class ProductsScreenTest {

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
          startDestination = NavGraph.Products.route
        ) {
          composable(NavGraph.Products.route) {
            ProductsScreen(
              navController = navController,
            )
          }
        } // NavHost
      } // SimplistTheme
    } // setContent
  }

  @Test
  fun clickToggleFilterSection_isVisible() {
    composeRule.onNodeWithTag(TestTags.FILTER_SECTION).assertDoesNotExist()
    composeRule.onNodeWithContentDescription("Filter contents").performClick()
    composeRule.onNodeWithTag(TestTags.FILTER_SECTION).assertIsDisplayed()
  }

  @Test
  fun clickToggleDialog_isVisible() {
    composeRule.onNodeWithTag(TestTags.DIALOG).assertDoesNotExist()
    // test category names in FakeCategoryRepository
    composeRule.onNodeWithContentDescription("Add to Category 1")
      .performClick()
    composeRule.onNodeWithTag(TestTags.DIALOG).assertIsDisplayed()
  }

  @Test
  fun clickToggleEmptyCategoryProducts_isNotVisible() {
    composeRule.onNodeWithTag(TestTags.PRODUCT_GRID + " Category 4")
      .assertDoesNotExist()
    composeRule.onNodeWithContentDescription("Show/hide products Category 4")
      .performClick()
    composeRule.onNodeWithTag(TestTags.PRODUCT_GRID + " Category 4")
      .assertIsNotDisplayed()
  }

  @Test
  fun addProductToCategory_toggleCategoryProductsVisible() {
    // Add "Carrot" to Category 2
    composeRule.onNodeWithContentDescription("Add to Category 2")
      .performClick()
    composeRule.onNodeWithTag(TestTags.PRODUCT_NAME)
      .performTextInput("Carrot")
    composeRule.waitForIdle()
    composeRule.onNodeWithContentDescription("Add product to Category 2")
      .performClick()
    // Dismiss the dialog
    composeRule.onNodeWithTag(TestTags.DISMISS_DIALOG).performClick()

    // Show it on screen
    composeRule.onNodeWithContentDescription("Show/hide products Category 2")
      .performClick()
    composeRule.onNodeWithTag(
      TestTags.PRODUCT_GRID + " Category 2"
    ).assertExists()
    composeRule.onNode(
      hasText("Carrot") and
          hasAnyAncestor(
            hasTestTag(TestTags.PRODUCT_GRID + " Category 2")
          )
    ).assertIsDisplayed()
  }

}