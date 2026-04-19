package yegor.cheprasov.pokedex.features.settings.domain.di

import org.koin.dsl.module
import yegor.cheprasov.pokedex.features.settings.domain.use_cases.ObserveThemeModeUseCase
import yegor.cheprasov.pokedex.features.settings.domain.use_cases.ObserveThemeModeUseCaseImpl
import yegor.cheprasov.pokedex.features.settings.domain.use_cases.SetThemeModeUseCase
import yegor.cheprasov.pokedex.features.settings.domain.use_cases.SetThemeModeUseCaseImpl

val settingsDomainModule = module {
    factory<ObserveThemeModeUseCase> {
        ObserveThemeModeUseCaseImpl(
            themePreferencesRepository = get(),
        )
    }

    factory<SetThemeModeUseCase> {
        SetThemeModeUseCaseImpl(
            themePreferencesRepository = get(),
        )
    }
}
