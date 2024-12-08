package ru.lnkr.todo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Immutable
data class ExtendedColors(
    val supportSeparator: Color,
    val supportOverlay: Color,
    val labelPrimary: Color,
    val labelSecondary: Color,
    val labelTertiary: Color,
    val labelDisable: Color,
    val colorRed: Color,
    val colorGreen: Color,
    val colorBlue: Color,
    val colorGray: Color,
    val colorGrayLight: Color,
    val colorWhite: Color,
    val backPrimary: Color,
    val backSecondary: Color,
    val backElevated: Color,
)

val LocalExtendedColors = staticCompositionLocalOf {
    ExtendedColors(
        supportSeparator = Color.Unspecified,
        supportOverlay = Color.Unspecified,
        labelPrimary = Color.Unspecified,
        labelSecondary = Color.Unspecified,
        labelTertiary = Color.Unspecified,
        labelDisable = Color.Unspecified,
        colorRed = Color.Unspecified,
        colorGreen = Color.Unspecified,
        colorBlue = Color.Unspecified,
        colorGray = Color.Unspecified,
        colorGrayLight = Color.Unspecified,
        colorWhite = Color.Unspecified,
        backPrimary = Color.Unspecified,
        backSecondary = Color.Unspecified,
        backElevated = Color.Unspecified,
    )
}


private val LightColorScheme = lightColorScheme(
    primary = LabelPrimaryLight,
    onPrimary = LabelPrimaryLight,

    secondary = LabelSecondaryLight,
    onSecondary = LabelSecondaryLight,

    background = BackPrimaryLight,
    onBackground = LabelPrimaryLight,

    surface = BackSecondaryLight,
    onSurface = LabelPrimaryLight,

    error = ColorRedLight,
    onError = ColorWhiteLight,
)

private val DarkColorScheme = darkColorScheme(
    primary = LabelPrimaryLight,
    onPrimary = LabelPrimaryLight,

    secondary = LabelSecondaryLight,
    onSecondary = LabelSecondaryLight,

    background = BackPrimaryLight,
    onBackground = LabelPrimaryLight,

    surface = BackSecondaryLight,
    onSurface = LabelPrimaryLight,

    error = ColorRedLight,
    onError = ColorWhiteLight,
)

val shapes = Shapes(
    small = RoundedCornerShape(percent = 50),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(16.dp)
)

@Composable
fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val extendedColors = ExtendedColors(
        supportSeparator = SupportSeparatorLight,
        supportOverlay = SupportOverlayLight,
        labelPrimary = LabelPrimaryLight,
        labelSecondary = LabelSecondaryLight,
        labelTertiary = LabelTertiaryLight,
        labelDisable = LabelDisableLight,
        colorRed = ColorRedLight,
        colorGreen = ColorGreenLight,
        colorBlue = ColorBlueLight,
        colorGray = ColorGrayLight,
        colorGrayLight = ColorGrayLightLight,
        colorWhite = ColorWhiteLight,
        backPrimary = BackPrimaryLight,
        backSecondary = BackSecondaryLight,
        backElevated = BackElevatedLight,
    )


    CompositionLocalProvider(LocalExtendedColors provides extendedColors) {
        MaterialTheme(
            typography = Typography,
            shapes = shapes,
            content = content
        )
    }
}

object AppTheme {
    val colors: ExtendedColors
        @Composable
        get() = LocalExtendedColors.current
}

//@Composable
//fun AppTheme(
//    useDarkTheme: Boolean = isSystemInDarkTheme(),
//    content: @Composable () -> Unit
//) {
//    val colors = if (useDarkTheme) DarkColorScheme else LightColorScheme
//
//    MaterialTheme(
//        colorScheme = colors,
//        typography = Typography,
//        shapes = shapes,
//        content = content
//    )
//}
