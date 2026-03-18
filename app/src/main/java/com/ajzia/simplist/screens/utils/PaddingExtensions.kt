package com.ajzia.simplist.screens.utils

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection

fun PaddingValues.withExtraBottom(extra: Dp) = PaddingValues(
  top = calculateTopPadding(),
  bottom = calculateBottomPadding() + extra,
  start = calculateLeftPadding(LayoutDirection.Ltr),
  end = calculateRightPadding(LayoutDirection.Rtl)
)