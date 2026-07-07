package com.ajzia.simplist.screens.edit_list

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.ajzia.simplist.model.ProductDetails
import com.ajzia.simplist.model.ProductList
import com.ajzia.simplist.screens.components.DefaultScaffold
import com.ajzia.simplist.screens.utils.withExtraBottom
import com.ajzia.simplist.viewmodel.EditListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun EditListScreen(
  listId: Int,
  onBack: () -> Unit,
  editListViewModel: EditListViewModel = hiltViewModel()
) {
  val context = LocalContext.current
  val listState = rememberLazyListState()
  val scope = rememberCoroutineScope()

  var name by remember { mutableStateOf( "") }
  var colorIdx by remember { mutableIntStateOf(0) }

  val productsDetails = remember { mutableStateListOf<ProductDetails>() }
  var productNames: List<String> = mutableListOf()

  val products by editListViewModel.products.collectAsState(emptyList())

  val list by editListViewModel
    .getProductList(listId)
    .collectAsState(initial = null)

  DefaultScaffold(
    onBack = onBack,
    onSubmit = {
      if (name == "") {
        makeToast(context,
          "Add a name to the list!",
          Toast.LENGTH_SHORT
        )
      } else if (productsDetails.isEmpty()) {
        makeToast(context,
          "Add at least one product!",
          Toast.LENGTH_SHORT
        )
      } else if (listId != -1) {
        editListViewModel.updateProductList(
          list!!.copy(
            name = name,
            color = colorIdx,
            productsDetails = productsDetails,
          )
        )

        makeToast(context,
          "Successfully updated the list",
          Toast.LENGTH_LONG
        )
        onBack()

      } else {
        // Fix for changing screens before the list was inserted
        // I'll do State vars in VMs instead someday
        scope.launch {
           editListViewModel.insertProductList(
            ProductList(
              name = name,
              color = colorIdx,
              productsDetails = productsDetails
            )
          )

          // Toast and back on the main thread
          withContext(Dispatchers.Main) {
            makeToast(context,
              "Successfully added the list",
              Toast.LENGTH_LONG
            )
            onBack()
          }
        }
      }
    } // onSubmit
  ) { paddingValues ->

    LaunchedEffect(list) {
      if (list != null) {
        name = list!!.name
        colorIdx = list!!.color

        for (details in list!!.productsDetails) {
          productsDetails.add(details)
          productNames = productNames.plus(
            details.name.lowercase())
        }
      }
    }

    LaunchedEffect(productsDetails.size) {
      if (productsDetails.isNotEmpty()) {
        listState.animateScrollToItem(productsDetails.lastIndex)
      }
    }

    if (listId != -1 && list == null) return@DefaultScaffold

    LazyColumn(
      modifier = Modifier.fillMaxSize(),
      state = listState,
      contentPadding = paddingValues.withExtraBottom(72.dp)
    ) {

      item {
        ColorPicker(
          chosenColor = colorIdx
        ) { colorIdx = it }
      } // Color Picker

      item {
        EditListCard(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
          name = name,
          colorIdx = colorIdx,
          productsDetails = productsDetails,
          products = products,
          onNameChange = { name = it },
          onRemove = { index ->
            productNames = productNames.minus(
              productsDetails[index].name.lowercase()
            )
            productsDetails.removeAt(index)
          }, // onRemove
          onQuantityChange = { index, quantity ->
            val _quantity = checkQuantity(context, quantity)
            if (_quantity <= -1) {
              return@EditListCard
            }

            val details = productsDetails[index]
            if (_quantity == details.quantity) {
              return@EditListCard
            }
            productsDetails[index].quantity = _quantity
            productsDetails[index].isChecked = false
          }, // onQuantityChange
          onAdd = { productName, quantity ->
            if (productName == "") {
              return@EditListCard
            } else if (productName in productNames) {
              makeToast(
                context,
                "The product is already in the list",
                Toast.LENGTH_SHORT
              )
              return@EditListCard
            }

            val _quantity = checkQuantity(context, quantity)
            if (_quantity <= -1) {
              return@EditListCard
            }

            productNames = productNames.plus(productName)
            val _productName = productName
              .replaceFirstChar { c -> c.uppercase() }

            val details = ProductDetails(
              name = _productName,
              categoryId = Int.MAX_VALUE,
              isChecked = false,
              quantity = _quantity
            )
            productsDetails.add(details)
          } // onAdd
        )
      }

    }
  } // DefaultScaffold
}

private fun makeToast(
  context: Context,
  text: String,
  duration: Int
) {
  Toast.makeText(context, text, duration).show()
}

private fun checkQuantity(
  context: Context,
  quantity: String,
): Int {
  var _quantity = -1
  if (quantity != "") {
    try {
      _quantity = quantity.trim().toInt()
    } catch (_: Exception) {
      makeToast(
        context,
        "The quantity should be in the number format",
        Toast.LENGTH_SHORT
      )
      return _quantity
    }
  } else {
    _quantity = 0
  }

  if (_quantity < 0) {
    makeToast(
      context,
      "The quantity should be higher or equal to 0",
      Toast.LENGTH_SHORT
    )
  }
  return _quantity
}