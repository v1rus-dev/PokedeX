package yegor.cheprasov.pokedex.core.design.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// ─────────────────────────────────────────────────────────────────────────────
// Brand palette
// ─────────────────────────────────────────────────────────────────────────────

internal val BrandRed = Color(0xFFDC0A2D)

// ─────────────────────────────────────────────────────────────────────────────
// Extended color tokens
// ─────────────────────────────────────────────────────────────────────────────

@Immutable
data class PokedexColors(

    // ── App bars ──────────────────────────────────────────────────────────────
    val primary: Color,
    val onPrimary: Color,
    val primaryIcon: Color,

    // ── Bottom navigation ─────────────────────────────────────────────────────
    val navBar: Color,
    val navBarActive: Color,
    val navBarInactive: Color,

    // ── Backgrounds / Surfaces ────────────────────────────────────────────────
    val background: Color,
    val surface: Color,
    val surfaceBorder: Color,

    // ── List items ────────────────────────────────────────────────────────────
    val listSurface: Color,
    val listAccent: Color,

    // ── Text ──────────────────────────────────────────────────────────────────
    /** Основной контент: названия, заголовки */
    val textPrimary: Color,
    /** Вторичный: подзаголовки, лейблы, описания */
    val textSecondary: Color,
    /** Третичный: хинты, плейсхолдеры, timestamps */
    val textTertiary: Color,
    val textFieldBackground: Color,

    // ── Icons ─────────────────────────────────────────────────────────────────
    /** Иконки поверх primary (TopAppBar, FAB) */
    val iconOnPrimary: Color,
    /** Иконки на обычных поверхностях */
    val iconOnSurface: Color,
    /** Приглушённые / вспомогательные иконки */
    val iconMuted: Color,

    // ── Toolbar indicators ────────────────────────────────────────────────────
    val indicatorBlue: Color,
    val indicatorYellow: Color,
    val indicatorGreen: Color,

    val loadingFillColor: Color
)

// ─────────────────────────────────────────────────────────────────────────────
// Light theme
// ─────────────────────────────────────────────────────────────────────────────

private val LightBackground        = Color(0xFFFFFFFF)
private val LightSurface           = Color(0xFFFFFBFA)
private val LightBottomBar         = Color(0xFFA61C38)
private val LightBottomBarInactive = Color(0xFFFFDDE3)

internal val LightPokedexColors = PokedexColors(
    primary              = BrandRed,
    onPrimary            = Color.White,
    primaryIcon          = Color.White,

    navBar               = LightBottomBar,
    navBarActive         = Color.White,
    navBarInactive       = LightBottomBarInactive,

    background           = LightBackground,
    surface              = LightSurface,
    surfaceBorder        = Color(0xFFE7C9CF),

    listSurface          = Color(0xFFFFFDFC),
    listAccent           = Color(0xFFFFE5E6),

    textPrimary          = Color(0xFF291B1E),
    textSecondary        = Color(0xFF6B4A50),
    textTertiary         = Color(0xFFB09499),
    textFieldBackground = Color(0xFFF9F9F9),

    iconOnPrimary        = Color.White,
    iconOnSurface        = Color(0xFF5E4349),
    iconMuted            = Color(0xFFB09499),

    indicatorBlue        = Color(0xFF52C5F7),
    indicatorYellow      = Color(0xFFFFD447),
    indicatorGreen       = Color(0xFF56D57A),
    loadingFillColor = Color(0xFFF8D46E)
)

// ─────────────────────────────────────────────────────────────────────────────
// Dark theme (navy)
// ─────────────────────────────────────────────────────────────────────────────

internal val DarkPokedexColors = PokedexColors(
    primary              = Color(0xFFBF2F3A),
    onPrimary            = Color(0xFFFFE8EA),
    primaryIcon          = Color(0xFFFFE8EA),

    navBar               = Color(0xFF141F38),
    navBarActive         = Color(0xFFC8D6F5),
    navBarInactive       = Color(0xFF2A3A5C),

    background           = Color(0xFF0F1729),
    surface              = Color(0xFF162039),
    surfaceBorder        = Color(0xFF2A3A5C),

    listSurface          = Color(0xFF192242),
    listAccent           = Color(0xFF3D1A2E),

    textPrimary          = Color(0xFFC8D6F5),
    textSecondary        = Color(0xFF8898C0),
    textTertiary         = Color(0xFF4A5E88),
    textFieldBackground = Color(0xFF162039),

    iconOnPrimary        = Color.White,
    iconOnSurface        = Color(0xFFA8B8D8),
    iconMuted            = Color(0xFF5C7099),

    indicatorBlue        = Color(0xFF4DAFEC),
    indicatorYellow      = Color(0xFFF0C040),
    indicatorGreen       = Color(0xFF4ACF7A),
    loadingFillColor = Color(0xFFF8D46E)
)

// ─────────────────────────────────────────────────────────────────────────────
// Material 3 ColorScheme (внутренняя деталь — для стандартных M3 компонентов)
// ─────────────────────────────────────────────────────────────────────────────

val LightM3Scheme = lightColorScheme(
    primary              = BrandRed,
    onPrimary            = Color.White,
    primaryContainer     = Color(0xFFFFDAD9),
    onPrimaryContainer   = Color(0xFF40000B),
    secondary            = Color(0xFF8A3143),
    onSecondary          = Color.White,
    secondaryContainer   = Color(0xFFFFD9E0),
    onSecondaryContainer = Color(0xFF3A0816),
    tertiary             = Color(0xFF3B7F97),
    onTertiary           = Color.White,
    background           = LightBackground,
    onBackground         = Color(0xFF24181B),
    surface              = LightSurface,
    onSurface            = Color(0xFF24181B),
    surfaceVariant       = Color(0xFFF4DFE2),
    onSurfaceVariant     = Color(0xFF5E4349),
    outline              = Color(0xFFD4B5BC),
    outlineVariant       = Color(0xFFE9CDD2),
)

val DarkM3Scheme = darkColorScheme(
    primary              = Color(0xFFBF2F3A),
    onPrimary            = Color(0xFFFFE8EA),
    primaryContainer     = Color(0xFF3D1A2E),
    onPrimaryContainer   = Color(0xFFFFDADB),
    secondary            = Color(0xFF8898C0),
    onSecondary          = Color(0xFF162039),
    secondaryContainer   = Color(0xFF192242),
    onSecondaryContainer = Color(0xFFC8D6F5),
    tertiary             = Color(0xFF4DAFEC),
    onTertiary           = Color(0xFF0F1729),
    background           = Color(0xFF0F1729),
    onBackground         = Color(0xFFC8D6F5),
    surface              = Color(0xFF162039),
    onSurface            = Color(0xFFC8D6F5),
    surfaceVariant       = Color(0xFF192242),
    onSurfaceVariant     = Color(0xFFA8B8D8),
    outline              = Color(0xFF2A3A5C),
    outlineVariant       = Color(0xFF2A3A5C),
)

// ─────────────────────────────────────────────────────────────────────────────
// CompositionLocal
// ─────────────────────────────────────────────────────────────────────────────

val LocalPokedexColors = staticCompositionLocalOf { LightPokedexColors }
