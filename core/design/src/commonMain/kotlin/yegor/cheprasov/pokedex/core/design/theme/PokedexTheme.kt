package yegor.cheprasov.pokedex.core.design.theme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.pokedex.ui.theme.DarkPokedexColorScheme
import com.example.pokedex.ui.theme.DarkPokedexColors
import com.example.pokedex.ui.theme.LightPokedexColorScheme
import com.example.pokedex.ui.theme.LightPokedexColors
import com.example.pokedex.ui.theme.LocalPokedexColors
import com.example.pokedex.ui.theme.PokedexColors
import yegor.cheprasov.pokedex.features.settings.api.ThemeMode

@Composable
fun PokedexTheme(
    themeMode: ThemeMode = ThemeMode.System,
    content: @Composable () -> Unit,
) {
    val isDarkTheme = resolveDarkTheme(
        themeMode = themeMode,
        isSystemDarkTheme = isSystemInDarkTheme(),
    )
    val spacing = remember { PokedexSpacing() }
    val radii = remember { PokedexRadii() }
    val typography = rememberPokedexTypography()
    val colorScheme = if (isDarkTheme) DarkPokedexColorScheme else LightPokedexColorScheme
    val pokedexColors = if (isDarkTheme) DarkPokedexColors else LightPokedexColors
    val shapes = remember(radii) {
        Shapes(
            small = RoundedCornerShape(radii.small),
            medium = RoundedCornerShape(radii.medium),
            large = RoundedCornerShape(radii.large),
        )
    }
    val systemBarsStyle = remember(pokedexColors, isDarkTheme) {
        PokedexSystemBarsStyle(
            statusBarColor = pokedexColors.topAppBar,
            navigationBarColor = pokedexColors.bottomBar,
            preferLightStatusBarIcons = shouldUseLightSystemBarIcons(pokedexColors.topAppBar),
            preferLightNavigationBarIcons = shouldUseLightSystemBarIcons(pokedexColors.bottomBar),
        )
    }

    CompositionLocalProvider(
        LocalPokedexSpacing provides spacing,
        LocalPokedexRadii provides radii,
        LocalPokedexColors provides pokedexColors,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            shapes = shapes,
        ) {
            PokedexSystemBarsEffect(style = systemBarsStyle)
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = pokedexColors.appBackground,
                contentColor = colorScheme.onBackground,
            ) {
                content()
            }
        }
    }
}

object PokedexTheme {
    val colors: PokedexColors
        @Composable
        @ReadOnlyComposable
        get() = LocalPokedexColors.current

    val spacing: PokedexSpacing
        @Composable
        @ReadOnlyComposable
        get() = LocalPokedexSpacing.current

    val radii: PokedexRadii
        @Composable
        @ReadOnlyComposable
        get() = LocalPokedexRadii.current

    val typography
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.typography
}

internal fun resolveDarkTheme(
    themeMode: ThemeMode,
    isSystemDarkTheme: Boolean,
): Boolean {
    return when (themeMode) {
        ThemeMode.System -> isSystemDarkTheme
        ThemeMode.Light -> false
        ThemeMode.Dark -> true
    }
}

internal fun shouldUseLightSystemBarIcons(background: Color): Boolean {
    return background.luminance() <= 0.5f
}
