package com.ajzia.simplist.screens.archive

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.ajzia.simplist.screens.components.DefaultScreen
import com.ajzia.simplist.screens.components.list.ListCard
import com.ajzia.simplist.viewmodel.ListsViewModel

@Composable
fun ArchiveScreen(
  navController: NavController,
  listsViewModel: ListsViewModel = hiltViewModel()
) {

  val listState = rememberLazyListState()
  val archivedLists by listsViewModel.archivedLists.collectAsState(emptyList())

  DefaultScreen(navController) { paddingValues ->
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues),
      state = listState
    ) {

      items(archivedLists) { list ->
        ListCard(
          modifier = Modifier.fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
          productList = list,
          onCopy = { },
          onArchive = { listsViewModel.onArchive(list) },
          onDelete = { listsViewModel.deleteProductList(list) },
          isUnArchived = false
        )
      }
    } // LazyColumn
  } // DefaultScreen

}
