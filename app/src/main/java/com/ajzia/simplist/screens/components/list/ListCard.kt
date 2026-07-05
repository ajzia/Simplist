package com.ajzia.simplist.screens.components.list

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.ajzia.simplist.core.util.TestTags
import com.ajzia.simplist.model.Category
import com.ajzia.simplist.model.ProductList
import com.ajzia.simplist.ui.theme.AppDrawables

@Composable
fun ListCard(
  modifier: Modifier = Modifier,
  index: Int,
  productList: ProductList,
  categories: List<Category>,
  onProductCheck: (Int) -> Unit = {},
  onCheckAll: (List<Int>, Boolean) -> Unit = {_,_ -> },
  onEdit: () -> Unit = {},
  onArchive: () -> Unit,
  onCopy: () -> Unit,
  onDelete: () -> Unit = {},
  isUnArchived: Boolean = true
) {
  Card(
    modifier = modifier,
    shape = RoundedCornerShape(24.dp),
    elevation = CardDefaults.cardElevation(4.dp),
    colors = CardDefaults.cardColors(
      containerColor = (
        if (!isUnArchived) Color.LightGray
        else Color(productList.color)
      )
    ),
    border = BorderStroke(1.dp, Color.Gray)
  ) {
    Column(
      modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
      verticalArrangement = Arrangement.Center,
    ) {

      Text(
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier
          .fillMaxWidth(0.9f)
          .wrapContentSize()
          .testTag(TestTags.LIST_TITLE + " $index"),
        text = productList.name,
        color = (
          if (!isUnArchived) Color.Gray
          else Color.Unspecified
        ),
      )

      val _categories = categories.plus(
        Category(Int.MAX_VALUE, "Other")
      )

      for (category in _categories.sortedBy { it.id }) {
        val details = productList.productsDetails
          .withIndex()
          .filter { it.value.categoryId == category.id }
        if (details.isEmpty()) continue

        CategoryListLine(
          modifier = Modifier
            .padding(horizontal = 4.dp),
          name = category.name,
          color = Color(productList.color),
          isEnabled = isUnArchived,
          isChecked = (
              details.all { it.value.isChecked }
          )
        ) {
          onCheckAll(
            details.map { it.index },
            !details.all { it.value.isChecked }
          )
        }

        for(det in details) {
          ListLine(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 4.dp),
            details = det.value,
            color = Color(productList.color),
            onChecked = { onProductCheck(det.index) },
            isEnabled = isUnArchived,
          )
        }
      }


      Spacer(modifier = Modifier.height(8.dp))

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.End
      ) {

        if (isUnArchived) {
          BottomButton(
            icon = ImageVector.vectorResource(AppDrawables.EditList),
            description = "Edit list $index",
            onClick = { onEdit() }
          )
        }

        BottomButton(
          icon = ImageVector.vectorResource(AppDrawables.CopyList),
          onClick = { onCopy() },
          description = "Copy list to stash $index"
        )

        BottomButton(
          icon = (
            if (isUnArchived)
              ImageVector.vectorResource(AppDrawables.ArchiveList)
            else
              ImageVector.vectorResource(AppDrawables.UnarchiveList)
          ),
          onClick = { onArchive() },
          description = (
            if (isUnArchived) "Archive list $index"
            else "Unarchive list $index"
          ),
        )

        if (!isUnArchived) {
          BottomButton(
            icon = ImageVector.vectorResource(AppDrawables.DeleteList),
            onClick = { onDelete() },
            description = "Delete list $index"
          )
        }
      } // BottomButtons

    } // Column
  } // Card
}