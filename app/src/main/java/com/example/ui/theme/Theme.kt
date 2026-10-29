package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme =
  lightColorScheme(
    primary = PrimaryGreen,
    onPrimary = Color.White,
    primaryContainer = SoftGreenSurface,
    onPrimaryContainer = DarkestGreen,
    secondary = DeepBrandGreen,
    onSecondary = Color.White,
    background = PrimaryBackground,
    onBackground = MainText,
    surface = PureCardBackground,
    onSurface = MainText,
    surfaceVariant = VeryLightGreenSurface,
    onSurfaceVariant = SecondaryText,
    outline = Borders,
    error = ErrorColor
  )

private val DarkColorScheme =
  darkColorScheme(
    primary = PrimaryGreen,
    onPrimary = Color.White,
    primaryContainer = DeepBrandGreen,
    onPrimaryContainer = SoftGreenSurface,
    secondary = SoftGreenSurface,
    onSecondary = DarkestGreen,
    background = Color(0xFF101814),
    onBackground = Color(0xFFE1EAE3),
    surface = Color(0xFF18231D),
    onSurface = Color(0xFFE1EAE3),
    surfaceVariant = Color(0xFF223028),
    onSurfaceVariant = Color(0xFF87938A),
    outline = Color(0xFF334438),
    error = ErrorColor
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }
      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
