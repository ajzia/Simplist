package com.ajzia.simplist.ui.theme

import androidx.compose.ui.graphics.Color

val theme_light_primary = Color(0xFF3A7D6C)
val theme_light_onPrimary = Color(0xFFFFFFFF)
val theme_light_primaryContainer = Color(0xFFA7C4BC)
val theme_light_onPrimaryContainer = Color(0xFF1F2A24)
val theme_light_inversePrimary = Color(0xFF2F6F61)
val theme_light_secondary = Color(0xFFD1E5E0)
val theme_light_onSecondary = Color(0xFF1F2A24)
val theme_light_secondaryContainer = Color(0xFFE6F1ED)
val theme_light_onSecondaryContainer = Color(0xFF1F2A24)
val theme_light_tertiary = Color(0xFFF4A261)
val theme_light_onTertiary = Color(0xFF1F2A24)
val theme_light_tertiaryContainer = Color(0xFFFFE2C6)
val theme_light_onTertiaryContainer = Color(0xFF1F2A24)
val theme_light_background = Color(0xFFF6F7F5)
val theme_light_onBackground = Color(0xFF1F2A24)
val theme_light_surface = Color(0xFFFFFFFF)
val theme_light_onSurface = Color(0xFF1F2A24)
val theme_light_surfaceVariant = Color(0xFFE3ECE8)
val theme_light_onSurfaceVariant = Color(0xFF6B7B73)
val theme_light_surfaceTint = Color(0xFF3A7D6C)
val theme_light_inverseSurface = Color(0xFF1F2A24)
val theme_light_inverseOnSurface = Color(0xFFF6F7F5)
val theme_light_error = Color(0xFFB3261E)
val theme_light_onError = Color(0xFFFFFFFF)
val theme_light_errorContainer = Color(0xFFFFDAD6)
val theme_light_onErrorContainer = Color(0xFF410002)
val theme_light_outline = Color(0xFF6B7B73)
val theme_light_outlineVariant = Color(0xFFA7C4BC)
val theme_light_scrim = Color(0xFF000000)
val theme_light_surfaceBright = Color(0xFFFFFFFF)
val theme_light_surfaceDim = Color(0xFFEDEEEB)
val theme_light_surfaceContainer = Color(0xFFF1F3F1)
val theme_light_surfaceContainerLow = Color(0xFFF6F7F5)
val theme_light_surfaceContainerHigh = Color(0xFFE9ECE9)
val theme_light_surfaceContainerHighest = Color(0xFFE3E6E3)
val theme_light_surfaceContainerLowest = Color(0xFFFFFFFF)


val theme_dark_primary = Color(0xFF4A6A5F)
val theme_dark_onPrimary = Color(0xFFE6EFEA)
val theme_dark_primaryContainer = Color(0xFF3E5F58)
val theme_dark_onPrimaryContainer = Color(0xFFE6EFEA)
val theme_dark_inversePrimary = Color(0xFFA7C4BC)
val theme_dark_secondary = Color(0xFF3E5F58)
val theme_dark_onSecondary = Color(0xFFE6EFEA)
val theme_dark_secondaryContainer = Color(0xFF2A3A35)
val theme_dark_onSecondaryContainer = Color(0xFFA7B7B0)
val theme_dark_tertiary = Color(0xFF9E7340)
val theme_dark_onTertiary = Color(0xFF121714)
val theme_dark_tertiaryContainer = Color(0xFF5A3D22)
val theme_dark_onTertiaryContainer = Color(0xFFE6EFEA)
val theme_dark_background = Color(0xFF121714)
val theme_dark_onBackground = Color(0xFFE6EFEA)
val theme_dark_surface = Color(0xFF1A211D)
val theme_dark_onSurface = Color(0xFFE6EFEA)
val theme_dark_surfaceVariant = Color(0xFF26312C)
val theme_dark_onSurfaceVariant = Color(0xFFA7B7B0)
val theme_dark_surfaceTint = Color(0xFF2F6F61)
val theme_dark_inverseSurface = Color(0xFFE6EFEA)
val theme_dark_inverseOnSurface = Color(0xFF121714)
val theme_dark_error = Color(0xFFFFB4A9)
val theme_dark_onError = Color(0xFF690005)
val theme_dark_errorContainer = Color(0xFF93000A)
val theme_dark_onErrorContainer = Color(0xFFFFDAD6)
val theme_dark_outline = Color(0xFFA7B7B0)
val theme_dark_outlineVariant = Color(0xFF3E5F58)
val theme_dark_scrim = Color(0xFF000000)
val theme_dark_surfaceBright = Color(0xFF2A332F)
val theme_dark_surfaceDim = Color(0xFF121714)
val theme_dark_surfaceContainer = Color(0xFF1A211D)
val theme_dark_surfaceContainerLow = Color(0xFF161C18)
val theme_dark_surfaceContainerHigh = Color(0xFF222B26)
val theme_dark_surfaceContainerHighest = Color(0xFF2A3530)
val theme_dark_surfaceContainerLowest = Color(0xFF0E120F)

data class CardColor(
  val cardColor: Color,
  val checkboxColor: Color
)

val theme_dark_card_color_palette = listOf(
  CardColor(Color(0xFF3E5F58), Color(0xFFA7C4BC)),
  CardColor(Color(0xFF446B63), Color(0xFFB8D8D0)),
  CardColor(Color(0xFF3F6860), Color(0xFFA7D8CA)),
  CardColor(Color(0xFF4E5F45), Color(0xFFAFC99D)),
  CardColor(Color(0xFF4E593B), Color(0xFFA9C36E)),
  CardColor(Color(0xFF5C5337), Color(0xFFE1C35C)),
  CardColor(Color(0xFF5B4A38), Color(0xFFE6B67E)),
  CardColor(Color(0xFF675444), Color(0xFFE5BE8F)),
  CardColor(Color(0xFF6A4C37), Color(0xFFF2A65A)),
  CardColor(Color(0xFF634645), Color(0xFFD89A95)),
  CardColor(Color(0xFF524768), Color(0xFFB6A8D8)),
  CardColor(Color(0xFF465675), Color(0xFF9DB5EA)),
  CardColor(Color(0xFF405B70), Color(0xFF8FC5F0)),
  CardColor(Color(0xFF3F6873), Color(0xFF84D0DD)),
  CardColor(Color(0xFF37403C), Color(0xFFA7B7B0)),
)

val theme_light_card_color_palette = listOf(
  CardColor(Color(0xFFA7C4BC),  Color(0xFF3A7D6C)),
  CardColor(Color(0xFFBFD6C4),  Color(0xFF5E8C6A)),
  CardColor(Color(0xFFC9DDD5),  Color(0xFF2F6F61)),
  CardColor(Color(0xFFD9ECE5),  Color(0xFF4C8A7B)),
  CardColor(Color(0xFFD9E3BC),  Color(0xFF7B9644)),
  CardColor(Color(0xFFF1E3B5),  Color(0xFFC9A631)),
  CardColor(Color(0xFFE7DCC8),  Color(0xFFC28A52)),
  CardColor(Color(0xFFF2E5D5),  Color(0xFFD39A68)),
  CardColor(Color(0xFFF8D6BE),  Color(0xFFF4A261)),
  CardColor(Color(0xFFE8C8C6),  Color(0xFFC46D68)),
  CardColor(Color(0xFFD9D4E7),  Color(0xFF8D7BC2)),
  CardColor(Color(0xFFCFDDF4),  Color(0xFF6D8FD6)),
  CardColor(Color(0xFFCDE4F2),  Color(0xFF5B9FD6)),
  CardColor(Color(0xFFD7EDF2),  Color(0xFF58A9B8)),
  CardColor(Color(0xFFE3ECE8),  Color(0xFF6B7B73)),
)