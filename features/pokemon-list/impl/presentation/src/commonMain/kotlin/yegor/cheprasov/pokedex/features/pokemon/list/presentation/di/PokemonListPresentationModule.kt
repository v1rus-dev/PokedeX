package yegor.cheprasov.pokedex.features.pokemon.list.presentation.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import yegor.cheprasov.pokedex.core.common.mapper.Mapper
import yegor.cheprasov.pokedex.features.pokemon.list.presentation.PokemonListViewModel
import yegor.cheprasov.pokedex.features.pokemon.list.presentation.mappers.PokemonUiMapper
import yegor.cheprasov.pokedex.features.pokemon.list.presentation.models.PokemonUiModel
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonModel

val mappers = module {
    factory<Mapper<PokemonModel, PokemonUiModel>> { PokemonUiMapper() }
}

val pokemonListPresentationModule = module {
    includes(mappers)
    viewModelOf(::PokemonListViewModel)
}
