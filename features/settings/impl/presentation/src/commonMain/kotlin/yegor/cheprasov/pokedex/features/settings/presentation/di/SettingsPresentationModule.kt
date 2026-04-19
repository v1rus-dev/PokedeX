package yegor.cheprasov.pokedex.features.settings.presentation.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import yegor.cheprasov.pokedex.features.settings.presentation.SettingsViewModel

val settingsPresentationModule = module {
    viewModelOf(::SettingsViewModel)
}
