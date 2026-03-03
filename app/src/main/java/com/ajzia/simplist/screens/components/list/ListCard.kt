package com.ajzia.simplist.screens.components.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ajzia.simplist.R
import com.ajzia.simplist.model.ProductDetails
import com.ajzia.simplist.model.ProductList
import com.ajzia.simplist.ui.theme.Amber100

@Composable
fun ListCard(
  modifier: Modifier = Modifier,
  productList: ProductList,
  onProductCheck: (Int) -> Unit = {},
  onEdit: () -> Unit = {},
  onArchive: () -> Unit,
  onCopy: () -> Unit,
  onDelete: () -> Unit = {},
  isUnArchived: Boolean = true
) {

  Card(
    modifier = modifier,
    shape = RoundedCornerShape(24.dp),
    elevation = CardDefaults.cardElevation(8.dp),
    colors = CardDefaults.cardColors(
      containerColor = (
        if (!isUnArchived) Color.LightGray
        else Color(productList.color)
      )
    )
  ) {
    Column(
      modifier = Modifier.padding(vertical = 8.dp),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {

      Text(
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier
          .fillMaxWidth(0.9f)
          .wrapContentSize(),
        text = productList.name,
        color = (
          if (!isUnArchived) Color.Gray
          else Color.Unspecified
        ),
      )

      for((index, details) in productList.productsDetails.withIndex()) {
        ListLine(
          modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
          details = details,
          color = Color(productList.color),
          onChecked = { onProductCheck(index) },
          isEnabled = isUnArchived,
        )
      }


      Spacer(modifier = Modifier.height(8.dp))

      Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.End
      ) {

        if (isUnArchived) {
          BottomButton(
            icon = ImageVector.vectorResource(R.drawable.outline_edit_24),
            description = "Edit list",
            onClick = { onEdit() }
          )
        }

        BottomButton(
          icon = ImageVector.vectorResource(R.drawable.outline_content_copy_24),
          onClick = { onCopy() },
          description = "Copy list to stash"
        )

        BottomButton(
          icon = (
            if (isUnArchived)
              ImageVector.vectorResource(R.drawable.outline_archive_24)
            else
              ImageVector.vectorResource(R.drawable.outline_unarchive_24)
          ),
          onClick = { onArchive() },
          description = (
            if (isUnArchived) "Archive list"
            else "Unarchive list"
          ),
        )

        if (!isUnArchived) {
          BottomButton(
            icon = ImageVector.vectorResource(R.drawable.outline_delete_24),
            onClick = { onDelete() },
            description = "Delete list"
          )
        }
      } // BottomButtons

    } // Column
  } // Card
}