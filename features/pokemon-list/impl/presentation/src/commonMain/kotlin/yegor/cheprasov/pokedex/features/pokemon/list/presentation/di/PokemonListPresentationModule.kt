package yegor.cheprasov.pokedex.features.pokemon.list.presentation.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import yegor.cheprasov.pokedex.features.pokemon.list.presentation.PokemonListViewModel

val pokemonListPresentationModule = module {
    viewModelOf(::PokemonListViewModel)
}
