package yegor.cheprasov.pokedex.features.settings.data.storage

import Foundation.NSUserDefaults
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import yegor.cheprasov.pokedex.features.settings.api.ThemeMode

actual class PlatformThemePreferencesStore actual constructor() : ThemePreferencesStore {

    private val userDefaults = NSUserDefaults.standardUserDefaults
    private val themeModeMutable = MutableStateFlow(loadThemeMode())

    actual override val themeMode: StateFlow<ThemeMode> = themeModeMutable

    actual override suspend fun setThemeMode(themeMode: ThemeMode) {
        if (themeModeMutable.value == themeMode) {
            return
        }

        userDefaults.setObject(themeMode.name, forKey = THEME_MODE_KEY)
        themeModeMutable.value = themeMode
    }

    private fun loadThemeMode(): ThemeMode {
        val storedValue = userDefaults.stringForKey(THEME_MODE_KEY)
        return storedValue
            ?.let(::safeThemeMode)
            ?: ThemeMode.System
    }

    private companion object {
        const val THEME_MODE_KEY = "theme_mode"
    }
}

private fun safeThemeMode(value: String): ThemeMode? {
    return ThemeMode.entries.firstOrNull { themeMode -> themeMode.name == value }
}
