package com.ajzia.simplist.screens.edit_list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ajzia.simplist.core.util.TestTags
import com.ajzia.simplist.model.Product
import com.ajzia.simplist.model.ProductDetails
import com.ajzia.simplist.ui.theme.Blue100

@Composable
fun EditListCard(
  modifier: Modifier = Modifier,
  name: String,
  color: Color,
  productsDetails: List<ProductDetails>,
  products: List<Product>,
  onNameChange: (String) -> Unit,
  onRemove: (Int) -> Unit,
  onQuantityChange: (Int, String) -> Unit,
  onAdd: (String, String) -> Unit,
) {
  Card(
    modifier = modifier,
    shape = RoundedCornerShape(24.dp),
    elevation = CardDefaults.cardElevation(4.dp),
    colors = CardDefaults.cardColors(containerColor = color),
    border = BorderStroke(1.dp, Color.Gray)
  ) {
    Column(
      modifier = Modifier.padding(8.dp),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {

      TextField(
        textStyle = MaterialTheme.typography.bodyLarge,
        modifier = Modifier
          .fillMaxWidth(0.9f)
          .testTag(TestTags.TITLE_FIELD),
        value = name,
        onValueChange = { onNameChange(it) },
        placeholder = { Text("Enter list name") },
        colors = TextFieldDefaults.colors(
          focusedContainerColor = Color.Transparent,
          unfocusedContainerColor = Color.Transparent,
          disabledContainerColor = Color.Transparent,
          focusedIndicatorColor = Color.Unspecified,
          unfocusedIndicatorColor = Color.Unspecified,
          disabledIndicatorColor= Color.Unspecified,
        ),
        keyboardOptions = KeyboardOptions(
          keyboardType = KeyboardType.Text,
          imeAction = ImeAction.Next
        ),
      )

      val modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 4.dp)

        for ((i, details) in productsDetails.withIndex()) {
          EditListLine(
            modifier = modifier,
            name = details.name,
            prodQuantity = details.quantity,
            isChecked = details.isChecked,
            color = color,
            products = products,
            index = i.toString(),
            quantityTag = TestTags.CHANGEABLE_QUANTITY_FIELD,
            onRemove = { onRemove(i) },
            onQuantityChange = {
              onQuantityChange(i, it)
            }
          )
        } // EditListLine

      EditListLine(
        modifier = modifier,
        color = color,
        products = products,
        onAdd = onAdd
      )

      Spacer(modifier = Modifier.height(8.dp))

    } // Column
  } // Card
}

@Composable
@Preview(showBackground = true)
fun EditListCardPreview() {
  val details = listOf(
    ProductDetails("Carrot", 1, true, 2),
    ProductDetails("Banana", 1, true, 0),
    ProductDetails("Apple", 1, false, 100),
    ProductDetails("Tomato", 1, true, 3),
    ProductDetails("Cucumber", 1, false, 8),
    ProductDetails("Watermelon", 1, true, 2),
  )

  EditListCard(
    name = "",
    color = Blue100,
    productsDetails = details,
    products = emptyList(),
    onNameChange = {},
    onRemove = {},
    onQuantityChange = {i, j -> },
    onAdd = {i, j -> },
  )
}