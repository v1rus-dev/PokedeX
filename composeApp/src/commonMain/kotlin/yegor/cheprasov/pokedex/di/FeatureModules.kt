package yegor.cheprasov.pokedex.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import yegor.cheprasov.pokedex.MainViewModel
import yegor.cheprasov.pokedex.features.favorites.presentation.di.favoritesPresentationModule
import yegor.cheprasov.pokedex.features.home.data.di.homeDataModule
import yegor.cheprasov.pokedex.features.home.domain.di.homeDomainModule
import yegor.cheprasov.pokedex.features.home.presentation.di.homePresentationModule
import yegor.cheprasov.pokedex.features.pokemon.data.di.pokemonDataModule
import yegor.cheprasov.pokedex.features.pokemon.details.presentation.di.pokemonDetailsPresentationModule
import yegor.cheprasov.pokedex.features.pokemon.domain.di.pokemonDomainModule
import yegor.cheprasov.pokedex.features.search.data.di.pokemonSearchDataModule
import yegor.cheprasov.pokedex.features.search.domain.di.pokemonSearchDomainModule
import yegor.cheprasov.pokedex.features.search.presentation.pokemonSearchPresentationModule
import yegor.cheprasov.pokedex.features.settings.data.di.settingsDataModule
import yegor.cheprasov.pokedex.features.settings.domain.di.settingsDomainModule
import yegor.cheprasov.pokedex.features.settings.presentation.di.settingsPresentationModule

val composeAppModule: Module = module {
    viewModelOf(::MainViewModel)
}

val featureModules: List<Module> = listOf(
    composeAppModule,
    homeDataModule,
    homeDomainModule,
    homePresentationModule,
    pokemonDataModule,
    pokemonDomainModule,
    pokemonDetailsPresentationModule,
    favoritesPresentationModule,
    pokemonSearchDataModule,
    pokemonSearchDomainModule,
    pokemonSearchPresentationModule,
    settingsDataModule,
    settingsDomainModule,
    settingsPresentationModule,
)
