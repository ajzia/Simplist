package com.ajzia.simplist.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
  primary = theme_dark_primary,
  onPrimary = theme_dark_onPrimary,
  primaryContainer = theme_dark_primaryContainer,
  onPrimaryContainer = theme_dark_onPrimaryContainer,
  inversePrimary = theme_dark_inversePrimary,
  secondary = theme_dark_secondary,
  onSecondary = theme_dark_onSecondary,
  secondaryContainer = theme_dark_secondaryContainer,
  onSecondaryContainer = theme_dark_onSecondaryContainer,
  tertiary = theme_dark_tertiary,
  onTertiary = theme_dark_onTertiary,
  tertiaryContainer = theme_dark_tertiaryContainer,
  onTertiaryContainer = theme_dark_onTertiaryContainer,
  background = theme_dark_background,
  onBackground = theme_dark_onBackground,
  surface = theme_dark_surface,
  onSurface = theme_dark_onSurface,
  surfaceVariant = theme_dark_surfaceVariant,
  onSurfaceVariant = theme_dark_onSurfaceVariant,
  surfaceTint = theme_dark_surfaceTint,
  inverseSurface = theme_dark_inverseSurface,
  inverseOnSurface = theme_dark_inverseOnSurface,
  error = theme_dark_error,
  onError = theme_dark_onError,
  errorContainer = theme_dark_errorContainer,
  onErrorContainer = theme_dark_onErrorContainer,
  outline = theme_dark_outline,
  outlineVariant = theme_dark_outlineVariant,
  scrim = theme_dark_scrim,
  surfaceBright = theme_dark_surfaceBright,
  surfaceDim = theme_dark_surfaceDim,
  surfaceContainer = theme_dark_surfaceContainer,
  surfaceContainerLow = theme_dark_surfaceContainerLow,
  surfaceContainerHigh = theme_dark_surfaceContainerHigh,
  surfaceContainerHighest = theme_dark_surfaceContainerHighest,
  surfaceContainerLowest = theme_dark_surfaceContainerLowest
)

private val LightColorScheme = lightColorScheme(
  primary = theme_light_primary,
  onPrimary = theme_light_onPrimary,
  primaryContainer = theme_light_primaryContainer,
  onPrimaryContainer = theme_light_onPrimaryContainer,
  inversePrimary = theme_light_inversePrimary,
  secondary = theme_light_secondary,
  onSecondary = theme_light_onSecondary,
  secondaryContainer = theme_light_secondaryContainer,
  onSecondaryContainer = theme_light_onSecondaryContainer,
  tertiary = theme_light_tertiary,
  onTertiary = theme_light_onTertiary,
  tertiaryContainer = theme_light_tertiaryContainer,
  onTertiaryContainer = theme_light_onTertiaryContainer,
  background = theme_light_background,
  onBackground = theme_light_onBackground,
  surface = theme_light_surface,
  onSurface = theme_light_onSurface,
  surfaceVariant = theme_light_surfaceVariant,
  onSurfaceVariant = theme_light_onSurfaceVariant,
  surfaceTint = theme_light_surfaceTint,
  inverseSurface = theme_light_inverseSurface,
  inverseOnSurface = theme_light_inverseOnSurface,
  error = theme_light_error,
  onError = theme_light_onError,
  errorContainer = theme_light_errorContainer,
  onErrorContainer = theme_light_onErrorContainer,
  outline = theme_light_outline,
  outlineVariant = theme_light_outlineVariant,
  scrim = theme_light_scrim,
  surfaceBright = theme_light_surfaceBright,
  surfaceDim = theme_light_surfaceDim,
  surfaceContainer = theme_light_surfaceContainer,
  surfaceContainerLow = theme_light_surfaceContainerLow,
  surfaceContainerHigh = theme_light_surfaceContainerHigh,
  surfaceContainerHighest = theme_light_surfaceContainerHighest,
  surfaceContainerLowest = theme_light_surfaceContainerLowest
)

object SimplistTheme {
  val cardColorList: List<CardColor>
    @Composable
    @ReadOnlyComposable
    get() = LocalListColors.current
}

val LocalListColors = staticCompositionLocalOf<List<CardColor>> {
  error("No list colors provided")
}

@Composable
fun SimplistTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit
) {
  val colorScheme = when {
    dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
      val context = LocalContext.current
      if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    }
    darkTheme -> DarkColorScheme
    else -> LightColorScheme
  }

  val palette = if (darkTheme) {
    theme_dark_card_color_palette
  } else {
    theme_light_card_color_palette
  }

  CompositionLocalProvider(
    LocalListColors provides palette
  ) {
    MaterialTheme(
      colorScheme = colorScheme,
      typography = Typography,
      content = content
    )
  }
}