package com.ajzia.simplist.screens.edit_list

import android.content.Context
import android.widget.Toast
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
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.ajzia.simplist.model.ProductDetails
import com.ajzia.simplist.model.ProductList
import com.ajzia.simplist.ui.theme.DefaultCardColor
import com.ajzia.simplist.viewmodel.EditListViewModel

@Composable
fun EditListScreen(
  context: Context,
  listId: Int,
  onBack: () -> Unit,
  editListViewModel: EditListViewModel = hiltViewModel()
) {
  var name by remember { mutableStateOf( "") }
  var color by remember { mutableIntStateOf(DefaultCardColor.toArgb()) }

  val productsDetails = remember { mutableStateListOf<ProductDetails>() }
  var productNames: List<String> = mutableListOf()

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
      productNames = productNames.plus(details.name)
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
          productsDetails = productsDetails,
          color = Color(color),
          onNameChange = { name = it },
          onRemove = { index ->
            productNames = productNames.minus(productsDetails[index].name)
            productsDetails.removeAt(index)
          }, // onRemove
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

            var _quantity = 0
            if (quantity != "") {
              try {
                _quantity = quantity.trim().toInt()
              } catch (_: Exception) {
                makeToast(
                  context,
                  "The quantity should be in the number format",
                  Toast.LENGTH_SHORT
                )
                return@EditListCard
              }
            }

            val _productName = productName.trim()
              .lowercase().replaceFirstChar { c -> c.uppercase() }
            productNames.plus(_productName)

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
                  lastEdited = System.currentTimeMillis()
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
    colors = ButtonDefaults.buttonColors(
    containerColor = color,
    contentColor = Color.Black,
    ),
  ) { Text(text = text, style = MaterialTheme.typography.bodyLarge) }
}
