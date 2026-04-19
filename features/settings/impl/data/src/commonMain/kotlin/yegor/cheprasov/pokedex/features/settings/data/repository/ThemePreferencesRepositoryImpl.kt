package yegor.cheprasov.pokedex.features.settings.data.repository

import kotlinx.coroutines.flow.Flow
import yegor.cheprasov.pokedex.features.settings.api.ThemeMode
import yegor.cheprasov.pokedex.features.settings.data.storage.ThemePreferencesStore
import yegor.cheprasov.pokedex.features.settings.domain.repository.ThemePreferencesRepository

internal class ThemePreferencesRepositoryImpl(
    private val themePreferencesStore: ThemePreferencesStore,
) : ThemePreferencesRepository {

    override fun observeThemeMode(): Flow<ThemeMode> {
        return themePreferencesStore.themeMode
    }

    override suspend fun setThemeMode(themeMode: ThemeMode) {
        themePreferencesStore.setThemeMode(themeMode)
    }
}
