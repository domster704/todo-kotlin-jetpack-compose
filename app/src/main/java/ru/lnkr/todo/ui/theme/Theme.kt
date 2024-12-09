package ru.lnkr.todo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Immutable
data class CustomColors(
    val supportSeparator: Color,
    val supportOverlay: Color,
    val labelPrimary: Color,
    val labelSecondary: Color,
    val labelTertiary: Color,
    val labelDisable: Color,
    val colorRed: Color,
    val colorRedTransparent: Color,
    val colorGreen: Color,
    val colorBlue: Color,
    val colorBlueTransparent: Color,
    val colorGray: Color,
    val colorGrayLight: Color,
    val colorWhite: Color,
    val backPrimary: Color,
    val backSecondary: Color,
    val backElevated: Color,
)

@Immutable
data class CustomTypography(
    val largeTitle: TextStyle = TextStyle.Default,
    val largeTitleBold: TextStyle = TextStyle.Default,
    val title: TextStyle = TextStyle.Default,
    val titleBold: TextStyle = TextStyle.Default,
    val button: TextStyle = TextStyle.Default,
    val buttonBold: TextStyle = TextStyle.Default,
    val body: TextStyle = TextStyle.Default,
    val bodyBold: TextStyle = TextStyle.Default,
    val subhead: TextStyle = TextStyle.Default,
    val subheadBold: TextStyle = TextStyle.Default,
)


val LocalCustomColors = staticCompositionLocalOf {
    CustomColors(
        supportSeparator = Color.Unspecified,
        supportOverlay = Color.Unspecified,
        labelPrimary = Color.Unspecified,
        labelSecondary = Color.Unspecified,
        labelTertiary = Color.Unspecified,
        labelDisable = Color.Unspecified,
        colorRed = Color.Unspecified,
        colorRedTransparent = Color.Unspecified,
        colorGreen = Color.Unspecified,
        colorBlue = Color.Unspecified,
        colorBlueTransparent = Color.Unspecified,
        colorGray = Color.Unspecified,
        colorGrayLight = Color.Unspecified,
        colorWhite = Color.Unspecified,
        backPrimary = Color.Unspecified,
        backSecondary = Color.Unspecified,
        backElevated = Color.Unspecified,
    )
}

val LocalCustomTypography = staticCompositionLocalOf {
    CustomTypography(
        largeTitle = TextStyle.Default,
        largeTitleBold = TextStyle.Default,
        title = TextStyle.Default,
        titleBold = TextStyle.Default,
        button = TextStyle.Default,
        buttonBold = TextStyle.Default,
        body = TextStyle.Default,
        bodyBold = TextStyle.Default,
        subhead = TextStyle.Default,
        subheadBold = TextStyle.Default,
    )
}

val lightColors = CustomColors(
    supportSeparator = SupportSeparatorLight,
    supportOverlay = SupportOverlayLight,
    labelPrimary = LabelPrimaryLight,
    labelSecondary = LabelSecondaryLight,
    labelTertiary = LabelTertiaryLight,
    labelDisable = LabelDisableLight,
    colorRed = ColorRedLight,
    colorRedTransparent = ColorRedTransparent,
    colorGreen = ColorGreenLight,
    colorBlue = ColorBlueLight,
    colorBlueTransparent = ColorBlueTransparent,
    colorGray = ColorGrayLight,
    colorGrayLight = ColorGrayLightLight,
    colorWhite = ColorWhiteLight,
    backPrimary = BackPrimaryLight,
    backSecondary = BackSecondaryLight,
    backElevated = BackElevatedLight,
)

val darkColors = CustomColors(
    supportSeparator = SupportSeparatorDark,
    supportOverlay = SupportOverlayDark,
    labelPrimary = LabelPrimaryDark,
    labelSecondary = LabelSecondaryDark,
    labelTertiary = LabelTertiaryDark,
    labelDisable = LabelDisableDark,
    colorRed = ColorRedDark,
    colorRedTransparent = ColorRedTransparent,
    colorGreen = ColorGreenDark,
    colorBlue = ColorBlueDark,
    colorBlueTransparent = ColorBlueTransparent,
    colorGray = ColorGrayDark,
    colorGrayLight = ColorGrayLightDark,
    colorWhite = ColorWhiteDark,
    backPrimary = BackPrimaryDark,
    backSecondary = BackSecondaryDark,
    backElevated = BackElevatedDark,
)

@Composable
fun AppTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colors = if (isDarkTheme) darkColors else lightColors

    val customTypography = CustomTypography(
        largeTitle = Type.largeTitle,
        largeTitleBold = Type.largeTitleBold,
        title = Type.title,
        titleBold = Type.titleBold,
        button = Type.button,
        buttonBold = Type.buttonBold,
        body = Type.body,
        bodyBold = Type.bodyBold,
        subhead = Type.subhead,
        subheadBold = Type.subheadBold,
    )


    CompositionLocalProvider(
        LocalCustomColors provides colors,
        LocalCustomTypography provides customTypography,
    ) {
//        MaterialTheme(
//            typography = Typography,
//            content = content
//        )
        content()
    }
}


object AppTheme {
    val colors: CustomColors
        @Composable
        get() = LocalCustomColors.current

    val typography: CustomTypography
        @Composable
        get() = LocalCustomTypography.current
}