package com.github.astat1cc.datebook.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.github.astat1cc.datebook.core.ui.colors.greenDark
import com.github.astat1cc.datebook.core.ui.colors.purple
import com.github.astat1cc.datebook.core.ui.colors.green

private val LightColorPalette = lightColors(
    primary = greenDark,
    primaryVariant = green,
    secondary = purple

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun DatebookTheme(content: @Composable () -> Unit) {
    val colors = LightColorPalette


    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}