package com.ajzia.simplist.screens.edit_list

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.ajzia.simplist.model.ProductDetails
import com.ajzia.simplist.model.ProductList
import com.ajzia.simplist.ui.theme.DefaultCardColor
import com.ajzia.simplist.viewmodel.EditListViewModel

@Composable
fun EditListScreen(
  listId: Int,
  onBack: () -> Unit,
  editListViewModel: EditListViewModel = hiltViewModel()
) {
  val context = LocalContext.current

  var name by remember { mutableStateOf( "") }
  var color by remember { mutableIntStateOf(DefaultCardColor.toArgb()) }

  val productsDetails = remember { mutableStateListOf<ProductDetails>() }
  var productNames: List<String> = mutableListOf()

  val products by editListViewModel.products.collectAsState(emptyList())

  val list by editListViewModel
    .getProductList(listId)
    .collectAsState(initial = null)

  if (list == null) {
    CircularProgressIndicator()
  } else {
    name = list!!.name
    color = list!!.color

    for (details in list!!.productsDetails) {
      productsDetails.add(details)
      productNames = productNames.plus(
        details.name.lowercase())
    }
  }

  Scaffold { paddingValues ->
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .padding(top = 12.dp),
    ) {

      item {
        ColorPicker(
          chosenColor = Color(color)
        ) { color = it.toArgb() }
      } // Color Picker

      item {
        EditListCard(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
          name = name,
          color = Color(color),
          productsDetails = productsDetails,
          products = products,
          onNameChange = { name = it },
          onRemove = { index ->
            productNames = productNames.minus(
              productsDetails[index].name)
            productsDetails.removeAt(index)
          }, // onRemove
          onQuantityChange = { index, quantity ->
            val _quantity = checkQuantity(context, quantity)
            if (_quantity == -1) {
              return@EditListCard
            }

            val details = productsDetails[index]
            if (_quantity == details.quantity) {
              return@EditListCard
            }
            productsDetails[index].quantity = _quantity
            productsDetails[index].isChecked = false
          },
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
            if (_quantity == -1) {
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

      item {
        Spacer(modifier = Modifier.height(16.dp))
      }

      item {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
          horizontalArrangement = Arrangement.SpaceEvenly
        ) {
          Button("Back", color = Color(color)) { onBack() }

          Button("Submit", color = Color(color)) {
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
                  color = color,
                  productsDetails = productsDetails,
                )
              )

              makeToast(context,
                "Successfully updated the list",
                Toast.LENGTH_LONG
              )
              onBack()

            } else {
              editListViewModel.insertProductList(
                ProductList(
                  name = name,
                  color = color,
                  productsDetails = productsDetails
                )
              )

              makeToast(context,
                "Successfully added the list",
                Toast.LENGTH_LONG
              )

              onBack()
            } // if
          }
        }
      } // BottomButtons

    } // LazyColumn
  } // Scaffold
}

private fun makeToast(
  context: Context,
  text: String,
  duration: Int
) {
  Toast.makeText(context, text, duration).show()
}


@Composable
fun Button(
  text: String,
  color: Color,
  onClick: () -> Unit
) {
  Button(
    modifier = Modifier,
    onClick = onClick,
    border = BorderStroke(1.dp, Color.Gray),
    colors = ButtonDefaults.buttonColors(
      containerColor = color,
      contentColor = Color.Black,
    ),
  ) { Text(text = text, style = MaterialTheme.typography.bodyLarge) }
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