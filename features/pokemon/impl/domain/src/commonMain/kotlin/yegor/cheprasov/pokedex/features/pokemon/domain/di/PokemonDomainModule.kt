package yegor.cheprasov.pokedex.features.pokemon.domain.di

import org.koin.core.module.Module
import org.koin.dsl.module
import yegor.cheprasov.pokedex.features.pokemon.domain.use_cases.GetAllPokemonsUseCaseImpl
import yegor.cheprasov.pokedex.features.pokemon.domain.use_cases.GetPokemonUseCaseImpl
import yegor.cheprasov.pokedex.features.pokemon.use_cases.GetAllPokemonsUseCase
import yegor.cheprasov.pokedex.features.pokemon.use_cases.GetPokemonUseCase

val pokemonDomainModule: Module = module {
    factory<GetPokemonUseCase> {
        GetPokemonUseCaseImpl(get())
    }

    factory<GetAllPokemonsUseCase> {
        GetAllPokemonsUseCaseImpl(get())
    }
}