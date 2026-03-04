package com.ajzia.simplist.screens.products

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.ajzia.simplist.model.Product
import com.ajzia.simplist.screens.components.DefaultScreen
import com.ajzia.simplist.viewmodel.ProductsViewModel

@Composable
fun ProductsScreen(
  navController: NavController,
  context: Context,
  productsViewModel: ProductsViewModel = hiltViewModel()
) {

  val listState = rememberLazyListState()

  var openAlertDialog by remember { mutableStateOf(false) }
  var selectedCategory by remember { mutableStateOf("") }
  var productName by remember { mutableStateOf("") }

  val productsRoom by productsViewModel.productsRoom.collectAsState(emptyList())
  val categories by productsViewModel.categories.collectAsState(emptyList())


  if (openAlertDialog) {
    ProductDialog(
      productName = productName,
      onNameChange = { productName = it },
      categoryName = categories.first { it.id == selectedCategory }.name,
      onDismissRequest = {
        openAlertDialog = false
        productName = ""
      },
      onAdd = {
        if (productsRoom.any { it.name == productName }) { // lub w firebase
          Toast.makeText(
            context,
            "Product already exists in" +
                "..." + // TODO
                "category",
            Toast.LENGTH_SHORT
          ).show()

        } else if (productName != ""){
          productsViewModel.insertProduct(
            Product(
              name = productName,
              categoryId = selectedCategory,
            )
          )

          Toast.makeText(
            context,
            "Product successfully added",
            Toast.LENGTH_SHORT
          ).show()

          productName = ""
        }
      } // onAdd
    )
  }


  DefaultScreen(navController, false) { paddingValues ->
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues),
      state = listState
    ) {
      items(categories.sortedBy { it.name }) { category ->
        CategoryDisplay(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
          name = category.name,
          products = productsViewModel.getCategoryProducts(productsRoom, category.id),
          onAdd = {
            selectedCategory = category.id
            openAlertDialog = true
          },
          onRemove = { product ->
            productsViewModel.deleteProduct(product)
          }
        )
      }
    } // LazyColumn
  } // DefaultScreen
}