package com.example.pokedex.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// ─────────────────────────────────────────────────────────────────────────────
// Brand / base palette
// ─────────────────────────────────────────────────────────────────────────────

internal val BrandRed = Color(0xFFDC0A2D)

private val LightBackground       = Color(0xFFFFFFFF)
private val LightSurface          = Color(0xFFFFFBFA)
private val LightSurfaceVariant   = Color(0xFFF4DFE2)
private val LightOutline          = Color(0xFFD4B5BC)
private val LightBottomBar        = Color(0xFFA61C38)
private val LightBottomBarInactive = Color(0xFFFFDDE3)

private val DarkBackground        = Color(0xFF170F12)
private val DarkSurface           = Color(0xFF22171B)
private val DarkSurfaceVariant    = Color(0xFF39282E)
private val DarkOutline           = Color(0xFF77565E)
private val DarkBottomBar         = Color(0xFF6D1326)
private val DarkBottomBarInactive  = Color(0xFFF2BAC6)

// ─────────────────────────────────────────────────────────────────────────────
// Material 3 ColorScheme
// ─────────────────────────────────────────────────────────────────────────────

internal val LightPokedexColorScheme: ColorScheme = lightColorScheme(
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
    surfaceVariant       = LightSurfaceVariant,
    onSurfaceVariant     = Color(0xFF5E4349),
    outline              = LightOutline,
    outlineVariant       = Color(0xFFE9CDD2),
)

internal val DarkPokedexColorScheme: ColorScheme = darkColorScheme(
    primary              = Color(0xFFFFB3B6),
    onPrimary            = Color(0xFF680016),
    primaryContainer     = Color(0xFF9C1730),
    onPrimaryContainer   = Color(0xFFFFDADB),
    secondary            = Color(0xFFFFB2C3),
    onSecondary          = Color(0xFF561321),
    secondaryContainer   = Color(0xFF70303D),
    onSecondaryContainer = Color(0xFFFFD9E0),
    tertiary             = Color(0xFFA9D5E4),
    onTertiary           = Color(0xFF003645),
    background           = DarkBackground,
    onBackground         = Color(0xFFF5DEE2),
    surface              = DarkSurface,
    onSurface            = Color(0xFFF5DEE2),
    surfaceVariant       = DarkSurfaceVariant,
    onSurfaceVariant     = Color(0xFFE2C1C8),
    outline              = DarkOutline,
    outlineVariant       = Color(0xFF543A42),
)

// ─────────────────────────────────────────────────────────────────────────────
// Extended color system
// ─────────────────────────────────────────────────────────────────────────────

@Immutable
data class PokedexColors(
    // ── App bars ──────────────────────────────────────────────────────────────
    val topAppBar: Color,
    val onTopAppBar: Color,
    /** Иконки в TopAppBar: «назад», «меню», действия */
    val topAppBarIcon: Color,

    // ── Bottom navigation ─────────────────────────────────────────────────────
    val bottomBar: Color,
    val onBottomBar: Color,
    val bottomBarInactive: Color,

    // ── Backgrounds ───────────────────────────────────────────────────────────
    val appBackground: Color,
    val cardBackground: Color,
    val cardBorder: Color,

    // ── List items ────────────────────────────────────────────────────────────
    val listItemBackground: Color,
    val listItemAccent: Color,
    val listItemText: Color,

    // ── Icons ─────────────────────────────────────────────────────────────────
    /** Иконки на обычных поверхностях (карточки, списки) */
    val iconOnSurface: Color,
    /** Иконки поверх BrandRed (кнопки, FAB) */
    val iconOnPrimary: Color,
    /** Вспомогательные / задизейбленные иконки */
    val iconSubdued: Color,

    // ── Toolbar indicator lights ───────────────────────────────────────────────
    val toolbarIndicatorBlue: Color,
    val toolbarIndicatorYellow: Color,
    val toolbarIndicatorGreen: Color,
)

// ─────────────────────────────────────────────────────────────────────────────
// Instances
// ─────────────────────────────────────────────────────────────────────────────

internal val LightPokedexColors = PokedexColors(
    topAppBar              = BrandRed,
    onTopAppBar            = Color.White,
    topAppBarIcon          = Color.White,

    bottomBar              = LightBottomBar,
    onBottomBar            = Color.White,
    bottomBarInactive      = LightBottomBarInactive,

    appBackground          = LightBackground,
    cardBackground         = LightSurface,
    cardBorder             = Color(0xFFE7C9CF),

    listItemBackground     = Color(0xFFFFFDFC),
    listItemAccent         = Color(0xFFFFE5E6),
    listItemText           = Color(0xFF291B1E),

    iconOnSurface          = Color(0xFF5E4349),   // = onSurfaceVariant
    iconOnPrimary          = Color.White,
    iconSubdued            = Color(0xFFB09499),

    toolbarIndicatorBlue   = Color(0xFF52C5F7),
    toolbarIndicatorYellow = Color(0xFFFFD447),
    toolbarIndicatorGreen  = Color(0xFF56D57A),
)

internal val DarkPokedexColors = PokedexColors(
    topAppBar              = BrandRed,
    onTopAppBar            = Color.White,
    topAppBarIcon          = Color.White,

    bottomBar              = DarkBottomBar,
    onBottomBar            = Color(0xFFFFE2E8),
    bottomBarInactive      = DarkBottomBarInactive,

    appBackground          = DarkBackground,
    cardBackground         = Color(0xFF21161A),
    cardBorder             = Color(0xFF49343B),

    listItemBackground     = Color(0xFF281B20),
    listItemAccent         = Color(0xFF4E1825),
    listItemText           = Color(0xFFFFE6EA),

    iconOnSurface          = Color(0xFFE2C1C8),   // = onSurfaceVariant
    iconOnPrimary          = Color.White,
    iconSubdued            = Color(0xFF8F7178),

    toolbarIndicatorBlue   = Color(0xFF6CD6FF),
    toolbarIndicatorYellow = Color(0xFFFFDA5B),
    toolbarIndicatorGreen  = Color(0xFF6EED96),
)

// ─────────────────────────────────────────────────────────────────────────────
// CompositionLocal
// ─────────────────────────────────────────────────────────────────────────────

val LocalPokedexColors = staticCompositionLocalOf { LightPokedexColors }