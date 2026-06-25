package com.ajzia.simplist.screens.lists

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.ajzia.simplist.core.util.TestTags
import com.ajzia.simplist.nav.NavGraph
import com.ajzia.simplist.screens.components.DefaultScaffold
import com.ajzia.simplist.screens.components.list.ListCard
import com.ajzia.simplist.screens.utils.withExtraBottom
import com.ajzia.simplist.viewmodel.ListsViewModel

@Composable
fun ListsScreen(
  navController: NavController,
  listsViewModel: ListsViewModel = hiltViewModel()
) {

  val listState = rememberLazyListState()
  val lists by listsViewModel.lists.collectAsState()
  val categories by listsViewModel.categories.collectAsState(emptyList())
  val filter by listsViewModel.filter.collectAsState("last_modified_desc")

  DefaultScaffold(
    navController = navController,
    chosenFilter = filter,
    onSearch = { listsViewModel.osSearchTextChange(it) },
    onFilter = { listsViewModel.onFilterChange(it) },
  )  { paddingValues ->
    LazyColumn(
      modifier = Modifier.fillMaxSize(),
      state = listState,
      contentPadding = paddingValues.withExtraBottom(72.dp)
    ) {

      itemsIndexed(lists.filter { !it.isArchived }) { index, list ->
        ListCard(
          modifier = Modifier.fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .testTag(TestTags.SAVED_LIST + " $index"),
          index = index,
          productList = list,
          categories = categories,
          onProductCheck = { index ->
            listsViewModel.onCheckClick(list, index)
          },
          onCheckAll = { indexes, value ->
            listsViewModel.onCheckAll(list, indexes, value)
          },
          onEdit = { navController.navigate(
            NavGraph.EditList.createRoute(list.id)
          ) },
          onCopy = { },
          onArchive = { listsViewModel.onArchive(list) },
        )
      }
    } // LazyColumn
  } // DefaultScaffold

}
