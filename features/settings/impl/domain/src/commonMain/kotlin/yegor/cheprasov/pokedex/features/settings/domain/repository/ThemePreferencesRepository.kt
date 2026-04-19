package yegor.cheprasov.pokedex.features.settings.domain.repository

import kotlinx.coroutines.flow.Flow
import yegor.cheprasov.pokedex.features.settings.api.ThemeMode

interface ThemePreferencesRepository {
    fun observeThemeMode(): Flow<ThemeMode>

    suspend fun setThemeMode(themeMode: ThemeMode)
}
