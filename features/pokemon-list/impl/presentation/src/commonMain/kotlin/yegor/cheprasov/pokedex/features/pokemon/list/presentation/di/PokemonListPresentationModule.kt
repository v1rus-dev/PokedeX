package yegor.cheprasov.pokedex.features.pokemon.list.presentation.di

import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.koin.dsl.navigation3.navigation
import yegor.cheprasov.pokedex.core.design.animation.ProvideLocalAnimatedScope
import yegor.cheprasov.pokedex.features.pokemon.list.api.PokemonList
import yegor.cheprasov.pokedex.features.pokemon.list.presentation.PokemonListDestination
import yegor.cheprasov.pokedex.features.pokemon.list.presentation.PokemonListViewModel

@OptIn(KoinExperimentalAPI::class)
val pokemonListPresentationModule = module {
    viewModelOf(::PokemonListViewModel)

    navigation<PokemonList> {
        ProvideLocalAnimatedScope {
            PokemonListDestination()
        }
    }
}
