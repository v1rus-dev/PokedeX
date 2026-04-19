package yegor.cheprasov.pokedex.core.design.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import pokedex.core.resources.generated.resources.Res
import pokedex.core.resources.generated.resources.pokemon_solid

@Composable
internal fun rememberPokedexTypography(): Typography {
    val displayFontFamily = FontFamily(
        Font(
            resource = Res.font.pokemon_solid,
            weight = FontWeight.Normal,
        ),
    )

    return remember(displayFontFamily) {
        Typography(
            displayLarge = TextStyle(
                fontFamily = displayFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 44.sp,
                lineHeight = 48.sp,
                letterSpacing = 0.4.sp,
            ),
            displayMedium = TextStyle(
                fontFamily = displayFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 36.sp,
                lineHeight = 40.sp,
                letterSpacing = 0.3.sp,
            ),
            headlineMedium = TextStyle(
                fontFamily = displayFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 28.sp,
                lineHeight = 32.sp,
                letterSpacing = 0.2.sp,
            ),
            titleLarge = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp,
                lineHeight = 28.sp,
            ),
            titleMedium = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                lineHeight = 24.sp,
            ),
            bodyLarge = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 22.sp,
            ),
            bodyMedium = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 20.sp,
            ),
            labelLarge = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                lineHeight = 18.sp,
            ),
            labelMedium = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                lineHeight = 16.sp,
            ),
        )
    }
}
