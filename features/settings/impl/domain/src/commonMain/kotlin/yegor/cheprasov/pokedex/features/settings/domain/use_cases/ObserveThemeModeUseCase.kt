package yegor.cheprasov.pokedex.features.settings.domain.use_cases

import kotlinx.coroutines.flow.Flow
import yegor.cheprasov.pokedex.features.settings.api.ThemeMode
import yegor.cheprasov.pokedex.features.settings.domain.repository.ThemePreferencesRepository

fun interface ObserveThemeModeUseCase {
    operator fun invoke(): Flow<ThemeMode>
}

class ObserveThemeModeUseCaseImpl(
    private val themePreferencesRepository: ThemePreferencesRepository,
) : ObserveThemeModeUseCase {

    override fun invoke(): Flow<ThemeMode> {
        return themePreferencesRepository.observeThemeMode()
    }
}
