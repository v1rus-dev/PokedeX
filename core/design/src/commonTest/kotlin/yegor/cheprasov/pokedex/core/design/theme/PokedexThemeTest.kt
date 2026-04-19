package yegor.cheprasov.pokedex.core.design.theme

import androidx.compose.ui.graphics.Color
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import yegor.cheprasov.pokedex.features.settings.api.ThemeMode

class PokedexThemeTest {

    @Test
    fun `system mode follows device appearance`() {
        assertTrue(resolveDarkTheme(themeMode = ThemeMode.System, isSystemDarkTheme = true))
        assertFalse(resolveDarkTheme(themeMode = ThemeMode.System, isSystemDarkTheme = false))
    }

    @Test
    fun `light and dark modes override device appearance`() {
        assertFalse(resolveDarkTheme(themeMode = ThemeMode.Light, isSystemDarkTheme = true))
        assertTrue(resolveDarkTheme(themeMode = ThemeMode.Dark, isSystemDarkTheme = false))
    }

    @Test
    fun `system bar icons are selected from background luminance`() {
        assertTrue(shouldUseLightSystemBarIcons(LightPokedexColors.topAppBar))
        assertTrue(shouldUseLightSystemBarIcons(LightPokedexColors.bottomBar))
        assertTrue(shouldUseLightSystemBarIcons(DarkPokedexColors.topAppBar))
        assertTrue(shouldUseLightSystemBarIcons(DarkPokedexColors.bottomBar))
        assertFalse(shouldUseLightSystemBarIcons(Color.White))
        assertTrue(shouldUseLightSystemBarIcons(Color.Black))
    }
}
