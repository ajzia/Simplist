package com.ajzia.simplist.screens.lists

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
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

  DefaultScaffold(
    navController = navController,
    onSearch = { listsViewModel.osSearchTextChange(it) },
    onFilter = { listsViewModel.onFilterChange(it) },
  )  { paddingValues ->
    LazyColumn(
      modifier = Modifier.fillMaxSize(),
      state = listState,
      contentPadding = paddingValues.withExtraBottom(72.dp)
    ) {

      items(lists.filter { !it.isArchived }) { list ->
        ListCard(
          modifier = Modifier.fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
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
