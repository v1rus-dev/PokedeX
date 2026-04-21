package yegor.cheprasov.pokedex.core.design.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import pokedex.core.resources.generated.resources.Inter_18pt_Bold
import pokedex.core.resources.generated.resources.Inter_18pt_Medium
import pokedex.core.resources.generated.resources.Inter_18pt_Regular
import pokedex.core.resources.generated.resources.Inter_18pt_SemiBold
import pokedex.core.resources.generated.resources.Res
import pokedex.core.resources.generated.resources.pokemon_solid

internal val LocalPokedexTypography = staticCompositionLocalOf { Typography() }

@Composable
internal fun rememberPokedexTypography(): Typography {
    val displayFontFamily = FontFamily(
        Font(
            resource = Res.font.pokemon_solid,
            weight = FontWeight.Normal,
        ),
    )
    val interFontFamily = FontFamily(
        Font(
            resource = Res.font.Inter_18pt_Regular,
            weight = FontWeight.Normal,
        ),
        Font(
            resource = Res.font.Inter_18pt_Medium,
            weight = FontWeight.Medium,
        ),
        Font(
            resource = Res.font.Inter_18pt_SemiBold,
            weight = FontWeight.SemiBold,
        ),
        Font(
            resource = Res.font.Inter_18pt_Bold,
            weight = FontWeight.Bold,
        ),
    )

    return remember(displayFontFamily, interFontFamily) {
        val defaultTypography = Typography()

        Typography(
            displayLarge = defaultTypography.displayLarge.copy(
                fontFamily = displayFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 44.sp,
                lineHeight = 48.sp,
                letterSpacing = 0.4.sp,
            ),
            displayMedium = defaultTypography.displayMedium.copy(
                fontFamily = displayFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 36.sp,
                lineHeight = 40.sp,
                letterSpacing = 0.3.sp,
            ),
            displaySmall = defaultTypography.displaySmall.copy(
                fontFamily = displayFontFamily,
                fontWeight = FontWeight.Normal,
            ),
            headlineLarge = defaultTypography.headlineLarge.copy(
                fontFamily = displayFontFamily,
                fontWeight = FontWeight.Normal,
            ),
            headlineMedium = defaultTypography.headlineMedium.copy(
                fontFamily = displayFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 28.sp,
                lineHeight = 32.sp,
                letterSpacing = 0.2.sp,
            ),
            headlineSmall = defaultTypography.headlineSmall.copy(
                fontFamily = interFontFamily,
                fontWeight = FontWeight.SemiBold,
            ),
            titleLarge = defaultTypography.titleLarge.copy(
                fontFamily = interFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp,
                lineHeight = 28.sp,
            ),
            titleMedium = defaultTypography.titleMedium.copy(
                fontFamily = interFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                lineHeight = 24.sp,
            ),
            titleSmall = defaultTypography.titleSmall.copy(
                fontFamily = interFontFamily,
                fontWeight = FontWeight.Medium,
            ),
            bodyLarge = defaultTypography.bodyLarge.copy(
                fontFamily = interFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 22.sp,
            ),
            bodyMedium = defaultTypography.bodyMedium.copy(
                fontFamily = interFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 20.sp,
            ),
            bodySmall = defaultTypography.bodySmall.copy(
                fontFamily = interFontFamily,
                fontWeight = FontWeight.Normal,
            ),
            labelLarge = defaultTypography.labelLarge.copy(
                fontFamily = interFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                lineHeight = 18.sp,
            ),
            labelMedium = defaultTypography.labelMedium.copy(
                fontFamily = interFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                lineHeight = 16.sp,
            ),
            labelSmall = defaultTypography.labelSmall.copy(
                fontFamily = interFontFamily,
                fontWeight = FontWeight.Medium,
            ),
        )
    }
}
