package yegor.cheprasov.pokedex.di

import org.koin.core.module.Module
import yegor.cheprasov.pokedex.features.favorites.presentation.di.favoritesPresentationModule
import yegor.cheprasov.pokedex.features.pokemon.data.di.pokemonDataModule
import yegor.cheprasov.pokedex.features.pokemon.details.presentation.pokemonDetailsPresentationModule
import yegor.cheprasov.pokedex.features.pokemon.domain.di.pokemonDomainModule
import yegor.cheprasov.pokedex.features.pokemon.list.data.di.pokemonListDataModule
import yegor.cheprasov.pokedex.features.pokemon.list.domain.di.pokemonListDomainModule
import yegor.cheprasov.pokedex.features.pokemon.list.presentation.di.pokemonListPresentationModule
import yegor.cheprasov.pokedex.features.settings.presentation.di.settingsPresentationModule

val featureModules: List<Module> = listOf(
    pokemonDataModule,
    pokemonDomainModule,
    pokemonListDataModule,
    pokemonListDomainModule,
    pokemonListPresentationModule,
    pokemonDetailsPresentationModule,
    favoritesPresentationModule,
    settingsPresentationModule,
)
