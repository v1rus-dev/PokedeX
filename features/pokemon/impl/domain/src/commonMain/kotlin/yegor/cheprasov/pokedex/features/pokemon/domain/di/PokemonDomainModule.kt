package yegor.cheprasov.pokedex.features.pokemon.domain.di

import org.koin.core.module.Module
import org.koin.dsl.module
import yegor.cheprasov.pokedex.features.pokemon.domain.use_cases.GetPokemonUseCaseImpl
import yegor.cheprasov.pokedex.features.pokemon.domain.use_cases.HasPokemonsInDatabaseUseCaseImpl
import yegor.cheprasov.pokedex.features.pokemon.domain.use_cases.ObserveAllPokemonsUseCaseImpl
import yegor.cheprasov.pokedex.features.pokemon.domain.use_cases.SyncPokemonsUseCaseImpl
import yegor.cheprasov.pokedex.features.pokemon.use_cases.GetPokemonUseCase
import yegor.cheprasov.pokedex.features.pokemon.use_cases.HasPokemonsInDatabaseUseCase
import yegor.cheprasov.pokedex.features.pokemon.use_cases.ObserveAllPokemonsUseCase
import yegor.cheprasov.pokedex.features.pokemon.use_cases.SyncPokemonsUseCase
import yegor.cheprasov.pokedex.features.sync.data.api.SyncDataUseCase

val pokemonDomainModule: Module = module {
    factory<GetPokemonUseCase> {
        GetPokemonUseCaseImpl(get())
    }

    factory<SyncPokemonsUseCase> {
        SyncPokemonsUseCaseImpl(get())
    }

    factory<SyncDataUseCase> {
        get<SyncPokemonsUseCase>()
    }

    factory<HasPokemonsInDatabaseUseCase> {
        HasPokemonsInDatabaseUseCaseImpl(get())
    }

    factory<ObserveAllPokemonsUseCase> {
        ObserveAllPokemonsUseCaseImpl(get())
    }
}
