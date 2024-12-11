package ru.lnkr.todo.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Immutable
data class CustomColors(
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
    colorGreen = ColorGreenLight,
    colorBlue = ColorBlueLight,
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
    colorGreen = ColorGreenDark,
    colorBlue = ColorBlueDark,
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

@Preview(
    widthDp = 1800,
    heightDp = 1100,
)
@Composable
fun AppThemePreview() {
    Row(horizontalArrangement = Arrangement.spacedBy(64.dp)) {
        AppTheme(isDarkTheme = false) {
            Column(
                modifier = Modifier
                    .background(AppTheme.colors.backSecondary)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "Large title - 32/38",
                    style = AppTheme.typography.largeTitle,
                    color = AppTheme.colors.labelPrimary,
                )

                Text(
                    "Title - 20/32",
                    style = AppTheme.typography.title,
                    color = AppTheme.colors.labelPrimary,
                )

                Text(
                    "Button - 14/24",
                    style = AppTheme.typography.button,
                    color = AppTheme.colors.labelPrimary,
                )

                Text(
                    "Body - 16/20",
                    style = AppTheme.typography.body,
                    color = AppTheme.colors.labelPrimary,
                )

                Text(
                    "Subhead - 14/20",
                    style = AppTheme.typography.subhead,
                    color = AppTheme.colors.labelPrimary,
                )
            }
        }
        Column(verticalArrangement = Arrangement.spacedBy(100.dp)) {
            AppTheme(isDarkTheme = false) {
                Palette("Палитра - светлая ема")
            }

            AppTheme(isDarkTheme = true) {
                Palette("Палитра - темная тема")
            }
        }
    }
}

@Composable
fun Palette(text: String) {
    val boxModifier = Modifier
        .size(240.dp, 100.dp)
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text,
            style = AppTheme.typography.buttonBold
        )
        Row {
            Box(modifier = boxModifier.background(AppTheme.colors.supportSeparator))
            Box(modifier = boxModifier.background(AppTheme.colors.supportOverlay))
        }
        Row {
            Box(modifier = boxModifier.background(AppTheme.colors.labelPrimary))
            Box(modifier = boxModifier.background(AppTheme.colors.labelSecondary))
            Box(modifier = boxModifier.background(AppTheme.colors.labelTertiary))
            Box(modifier = boxModifier.background(AppTheme.colors.labelDisable))
        }
        Row {
            Box(modifier = boxModifier.background(AppTheme.colors.colorRed))
            Box(modifier = boxModifier.background(AppTheme.colors.colorGreen))
            Box(modifier = boxModifier.background(AppTheme.colors.colorBlue))
            Box(modifier = boxModifier.background(AppTheme.colors.colorGray))
            Box(modifier = boxModifier.background(AppTheme.colors.colorGrayLight))
            Box(modifier = boxModifier.background(AppTheme.colors.colorWhite))
        }
        Row {
            Box(modifier = boxModifier.background(AppTheme.colors.backPrimary))
            Box(modifier = boxModifier.background(AppTheme.colors.backSecondary))
            Box(modifier = boxModifier.background(AppTheme.colors.backElevated))
        }
    }
}