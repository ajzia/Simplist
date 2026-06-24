package com.ajzia.simplist.screens.lists

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
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
class ListsScreenTest {

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
  fun clickToggleSearchBar_isExtensionVisible() {
    composeRule.onNodeWithTag(TestTags.SEARCH_FIELD).assertIsNotEnabled()
    composeRule.onNodeWithTag(TestTags.SEARCH_FIELD).performClick()
    composeRule.onNodeWithTag(TestTags.SEARCH_FIELD).assertIsEnabled()
  }

  @Test
  fun clickToggleSearchBar_closeWhileExtensionVisible() {
    composeRule.onNodeWithTag(TestTags.SEARCH_FIELD).performClick()
    composeRule.onNodeWithContentDescription("Exit search").assertIsDisplayed()
    composeRule.onNodeWithContentDescription("Exit search").performClick()
    composeRule.onNodeWithContentDescription("Exit search").assertDoesNotExist()
    composeRule.onNodeWithTag(TestTags.SEARCH_FIELD).assertIsNotEnabled()
  }

  @Test
  fun clickToggleSearchBar_clearSearch() {
    composeRule.onNodeWithTag(TestTags.SEARCH_FIELD).assertIsNotEnabled()
    composeRule.onNodeWithTag(TestTags.SEARCH_FIELD).performClick()
    composeRule.onNodeWithContentDescription("Clear search").assertDoesNotExist()
    composeRule.onNodeWithTag(TestTags.SEARCH_FIELD).performTextInput("test")
    composeRule.onNodeWithContentDescription("Clear search").assertIsDisplayed()
    composeRule.onNodeWithContentDescription("Clear search").performClick()
    composeRule.onNodeWithContentDescription("Clear search").assertDoesNotExist()
  }
}
























