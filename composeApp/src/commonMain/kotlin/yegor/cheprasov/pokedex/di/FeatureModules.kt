package yegor.cheprasov.pokedex.di

import org.koin.core.module.Module
import yegor.cheprasov.pokedex.features.pokemon.details.presentation.pokemonDetailsPresentationModule
import yegor.cheprasov.pokedex.features.pokemon.list.data.di.pokemonListDataModule
import yegor.cheprasov.pokedex.features.pokemon.list.domain.di.pokemonListDomainModule
import yegor.cheprasov.pokedex.features.pokemon.list.presentation.di.pokemonListPresentationModule

val featureModules: List<Module> = listOf(
    pokemonListDataModule,
    pokemonListDomainModule,
    pokemonListPresentationModule,
    pokemonDetailsPresentationModule,
)
