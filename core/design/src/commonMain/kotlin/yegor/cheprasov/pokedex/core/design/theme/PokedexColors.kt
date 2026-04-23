package yegor.cheprasov.pokedex.core.design.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// ─────────────────────────────────────────────────────────────────────────────
// Brand palette
// ─────────────────────────────────────────────────────────────────────────────

internal val BrandRed = Color(0xFFDC0A2D)

@Immutable
data class PokedexColors(
    val primary: Color,
    val onPrimary: Color,
    val primaryIcon: Color,
    val navBar: Color,
    val navBarActive: Color,
    val navBarInactive: Color,
    val background: Color,
    val surface: Color,
    val surfaceBorder: Color,
    val listSurface: Color,
    val listAccent: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textTertiary: Color,
    val textFieldBackground: Color,
    val iconOnPrimary: Color,
    val iconOnSurface: Color,
    val iconMuted: Color,
    val indicatorBlue: Color,
    val indicatorYellow: Color,
    val indicatorGreen: Color,
    val loadingFillColor: Color,
    val shadowColor: Color,
)

// ─────────────────────────────────────────────────────────────────────────────
// Light theme
// ─────────────────────────────────────────────────────────────────────────────

private val LightBackground        = Color(0xFFFFFFFF)
private val LightSurface           = Color(0xFFFFFFFF)
private val LightBottomBar         = Color(0xFFA61C38)
private val LightBottomBarInactive = Color(0xFFE8E8E8)

internal val LightPokedexColors = PokedexColors(
    primary              = BrandRed,
    onPrimary            = Color.White,
    primaryIcon          = Color.White,

    navBar               = LightBottomBar,
    navBarActive         = Color.White,
    navBarInactive       = LightBottomBarInactive,

    background           = LightBackground,
    surface              = LightSurface,
    surfaceBorder        = Color(0xFFE2E2E2),

    listSurface          = Color(0xFFFAFAFA),
    listAccent           = Color(0xFFF0F0F0),

    textPrimary          = Color(0xFF1A1A1A),
    textSecondary        = Color(0xFF5C5C5C),
    textTertiary         = Color(0xFF9E9E9E),
    textFieldBackground  = Color(0xFFF5F5F5),

    iconOnPrimary        = Color.White,
    iconOnSurface        = Color(0xFF4A4A4A),
    iconMuted            = Color(0xFFAAAAAA),

    indicatorBlue        = Color(0xFF52C5F7),
    indicatorYellow      = Color(0xFFFFD447),
    indicatorGreen       = Color(0xFF56D57A),
    loadingFillColor     = Color(0xFFF8D46E),
    shadowColor          = Color(0x0D000000),
)

// ─────────────────────────────────────────────────────────────────────────────
// Dark theme
// ─────────────────────────────────────────────────────────────────────────────

internal val DarkPokedexColors = PokedexColors(
    primary              = Color(0xFFBF2F3A),
    onPrimary            = Color(0xFFFFE8EA),
    primaryIcon          = Color(0xFFFFE8EA),

    navBar               = Color(0xFF141F38),
    navBarActive         = Color.White,
    navBarInactive       = Color(0xFF3A3A3A),

    background           = Color(0xFF0F1729),
    surface              = Color(0xFF162039),
    surfaceBorder        = Color(0xFF2E2E2E),

    listSurface          = Color(0xFF1C1C1C),
    listAccent           = Color(0xFF252525),

    textPrimary          = Color(0xFFEAEAEA),
    textSecondary        = Color(0xFF9E9E9E),
    textTertiary         = Color(0xFF5C5C5C),
    textFieldBackground  = Color(0xFF1C1C1C),

    iconOnPrimary        = Color.White,
    iconOnSurface        = Color(0xFFBDBDBD),
    iconMuted            = Color(0xFF707070),

    indicatorBlue        = Color(0xFF4DAFEC),
    indicatorYellow      = Color(0xFFF0C040),
    indicatorGreen       = Color(0xFF4ACF7A),
    loadingFillColor     = Color(0xFFF8D46E),
    shadowColor          = Color(0x0DFFFFFF),
)

// ─────────────────────────────────────────────────────────────────────────────
// Material 3 ColorScheme
// ─────────────────────────────────────────────────────────────────────────────

val LightM3Scheme = lightColorScheme(
    primary              = BrandRed,
    onPrimary            = Color.White,
    primaryContainer     = Color(0xFFFFDAD9),
    onPrimaryContainer   = Color(0xFF40000B),
    secondary            = Color(0xFF5C5C5C),
    onSecondary          = Color.White,
    secondaryContainer   = Color(0xFFEEEEEE),
    onSecondaryContainer = Color(0xFF1A1A1A),
    tertiary             = Color(0xFF3B7F97),
    onTertiary           = Color.White,
    background           = LightBackground,
    onBackground         = Color(0xFF1A1A1A),
    surface              = LightSurface,
    onSurface            = Color(0xFF1A1A1A),
    surfaceVariant       = Color(0xFFF0F0F0),
    onSurfaceVariant     = Color(0xFF4A4A4A),
    outline              = Color(0xFFD0D0D0),
    outlineVariant       = Color(0xFFE5E5E5),
)

val DarkM3Scheme = darkColorScheme(
    primary              = Color(0xFFBF2F3A),
    onPrimary            = Color(0xFFFFE8EA),
    primaryContainer     = Color(0xFF3D1A2E),
    onPrimaryContainer   = Color(0xFFFFDADB),
    secondary            = Color(0xFF9E9E9E),
    onSecondary          = Color(0xFF1C1C1C),
    secondaryContainer   = Color(0xFF252525),
    onSecondaryContainer = Color(0xFFEAEAEA),
    tertiary             = Color(0xFF4DAFEC),
    onTertiary           = Color(0xFF0F1729),
    background           = Color(0xFF0F1729),
    onBackground         = Color(0xFFEAEAEA),
    surface              = Color(0xFF162039),
    onSurface            = Color(0xFFEAEAEA),
    surfaceVariant       = Color(0xFF1C1C1C),
    onSurfaceVariant     = Color(0xFFBDBDBD),
    outline              = Color(0xFF2E2E2E),
    outlineVariant       = Color(0xFF2E2E2E),
)

// ─────────────────────────────────────────────────────────────────────────────
// CompositionLocal
// ─────────────────────────────────────────────────────────────────────────────

val LocalPokedexColors = staticCompositionLocalOf { LightPokedexColors }
