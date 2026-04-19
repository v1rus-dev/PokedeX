package yegor.cheprasov.pokedex.features.settings.domain.use_cases

import yegor.cheprasov.pokedex.features.settings.api.ThemeMode
import yegor.cheprasov.pokedex.features.settings.domain.repository.ThemePreferencesRepository

fun interface SetThemeModeUseCase {
    suspend operator fun invoke(themeMode: ThemeMode)
}

class SetThemeModeUseCaseImpl(
    private val themePreferencesRepository: ThemePreferencesRepository,
) : SetThemeModeUseCase {

    override suspend fun invoke(themeMode: ThemeMode) {
        themePreferencesRepository.setThemeMode(themeMode)
    }
}
