package yegor.cheprasov.pokedex.features.settings.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import yegor.cheprasov.pokedex.features.settings.api.ThemeMode
import yegor.cheprasov.pokedex.features.settings.data.storage.ThemePreferencesStore

class ThemePreferencesRepositoryImplTest {

    @Test
    fun `repository exposes persisted theme mode and updates it`() = runBlocking {
        val store = FakeThemePreferencesStore(initialThemeMode = ThemeMode.Dark)
        val repository = ThemePreferencesRepositoryImpl(themePreferencesStore = store)

        assertEquals(ThemeMode.Dark, repository.observeThemeMode().first())

        repository.setThemeMode(ThemeMode.Light)

        assertEquals(ThemeMode.Light, repository.observeThemeMode().first())
    }
}

private class FakeThemePreferencesStore(
    initialThemeMode: ThemeMode,
) : ThemePreferencesStore {

    private val themeModeMutable = MutableStateFlow(initialThemeMode)

    override val themeMode = themeModeMutable

    override suspend fun setThemeMode(themeMode: ThemeMode) {
        themeModeMutable.value = themeMode
    }
}
