package yegor.cheprasov.pokedex.features.settings.data.storage

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import yegor.cheprasov.pokedex.features.settings.api.ThemeMode
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

actual class PlatformThemePreferencesStore actual constructor() : ThemePreferencesStore, KoinComponent {

    private val context by inject<Context>()

    private val sharedPreferences by lazy(LazyThreadSafetyMode.NONE) {
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    private val themeModeMutable = MutableStateFlow(loadThemeMode())

    actual override val themeMode: StateFlow<ThemeMode> = themeModeMutable

    actual override suspend fun setThemeMode(themeMode: ThemeMode) {
        if (themeModeMutable.value == themeMode) {
            return
        }

        sharedPreferences.edit()
            .putString(THEME_MODE_KEY, themeMode.name)
            .apply()

        themeModeMutable.value = themeMode
    }

    private fun loadThemeMode(): ThemeMode {
        val storedValue = sharedPreferences.getString(THEME_MODE_KEY, ThemeMode.System.name)
        return storedValue
            ?.let(::safeThemeMode)
            ?: ThemeMode.System
    }

    private companion object {
        const val PREFERENCES_NAME = "pokedex_theme_preferences"
        const val THEME_MODE_KEY = "theme_mode"
    }
}

private fun safeThemeMode(value: String): ThemeMode? {
    return ThemeMode.entries.firstOrNull { themeMode -> themeMode.name == value }
}
