package yegor.cheprasov.pokedex.features.settings.data.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import yegor.cheprasov.pokedex.features.settings.data.repository.ThemePreferencesRepositoryImpl
import yegor.cheprasov.pokedex.features.settings.data.storage.PlatformThemePreferencesStore
import yegor.cheprasov.pokedex.features.settings.data.storage.ThemePreferencesStore
import yegor.cheprasov.pokedex.features.settings.domain.repository.ThemePreferencesRepository

val settingsDataModule = module {
    singleOf(::PlatformThemePreferencesStore) { bind<ThemePreferencesStore>() }
    singleOf(::ThemePreferencesRepositoryImpl) { bind<ThemePreferencesRepository>() }
}
