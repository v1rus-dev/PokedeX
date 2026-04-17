package yegor.cheprasov.pokedex.features.pokemon.list.domain.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import yegor.cheprasov.pokedex.features.pokemon.list.domain.PokemonListApi

val pokemonListDomainModule = module {
    factoryOf(::PokemonListApi)
}
