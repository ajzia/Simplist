package com.ajzia.simplist.screens.edit_list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ajzia.simplist.core.util.TestTags
import com.ajzia.simplist.ui.theme.Blue100
import com.ajzia.simplist.ui.theme.CustomPurple
import com.ajzia.simplist.ui.theme.colors

@Composable
fun ColorPicker(
  chosenColor: Color,
  onColorClick: (Color) -> Unit
) {

  LazyRow(
    modifier = Modifier
      .fillMaxWidth()
      .padding(8.dp)
      .testTag(TestTags.COLOR_LIST),
    horizontalArrangement = Arrangement.SpaceEvenly
  ) {
    itemsIndexed(colors) { index, color ->
      ColorBox(
        modifier = Modifier.clickable(
          interactionSource = remember { MutableInteractionSource() },
          indication = ripple(
            color = CustomPurple,
            radius = 22.dp,
            bounded = false
          )
        ) { onColorClick(color) },
        color = color,
        isChosen = color == chosenColor,
        index = index
      )
    }
  }
}

@Composable
fun ColorBox(
  modifier: Modifier,
  color: Color,
  isChosen: Boolean,
  index: Int
) {
  Box(modifier = modifier
    .size(40.dp)
    .padding(4.dp)
    .clip(CircleShape)
    .border(
      width = (if (isChosen) 2.dp else 1.dp), shape = CircleShape,
      color = (if (isChosen) Color.Gray else Color.Gray)
    )
    .background(color)
    .testTag(TestTags.COLOR + " $index")
    .semantics {
      selected = isChosen
    }
  )
}

@Preview(showBackground = true)
@Composable
fun ColorPickerPreview() {
  var chosenColor: Color = Blue100
  ColorPicker(chosenColor) { color -> chosenColor = color }
}