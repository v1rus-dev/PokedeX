package yegor.cheprasov.pokedex.features.settings.data.storage

import kotlinx.coroutines.flow.StateFlow
import yegor.cheprasov.pokedex.features.settings.api.ThemeMode

internal interface ThemePreferencesStore {
    val themeMode: StateFlow<ThemeMode>

    suspend fun setThemeMode(themeMode: ThemeMode)
}

expect class PlatformThemePreferencesStore() : ThemePreferencesStore {
    override val themeMode: StateFlow<ThemeMode>

    override suspend fun setThemeMode(themeMode: ThemeMode)
}
