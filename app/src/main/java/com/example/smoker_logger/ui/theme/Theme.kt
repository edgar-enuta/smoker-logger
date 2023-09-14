package com.example.smoker_logger.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource

@Composable
fun SmokerloggerTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        darkColors(
        primary = colorResource(Purple200),
        primaryVariant = colorResource(Purple700),
        secondary = colorResource(Teal200)
        )
    } else {
        lightColors(
            primary = colorResource(Purple500),
            primaryVariant = colorResource(Purple700),
            secondary = colorResource(Teal200)
        )
    }

    MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
    )
}