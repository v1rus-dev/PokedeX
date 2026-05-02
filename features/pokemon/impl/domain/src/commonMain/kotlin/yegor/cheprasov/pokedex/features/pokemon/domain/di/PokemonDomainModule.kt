package yegor.cheprasov.pokedex.features.pokemon.domain.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import yegor.cheprasov.pokedex.features.pokemon.domain.use_cases.GetPokemonEvolutionChainUseCaseImpl
import yegor.cheprasov.pokedex.features.pokemon.domain.use_cases.GetPokemonUseCaseImpl
import yegor.cheprasov.pokedex.features.pokemon.domain.use_cases.HasPokemonsInDatabaseUseCaseImpl
import yegor.cheprasov.pokedex.features.pokemon.domain.use_cases.ObserveAllPokemonsUseCaseImpl
import yegor.cheprasov.pokedex.features.pokemon.domain.use_cases.ObservePokemonFavoriteStateUseCaseImpl
import yegor.cheprasov.pokedex.features.pokemon.domain.use_cases.SyncPokemonEvolutionsUseCaseImpl
import yegor.cheprasov.pokedex.features.pokemon.domain.use_cases.SyncPokemonsUseCaseImpl
import yegor.cheprasov.pokedex.features.pokemon.domain.use_cases.UpdatePokemonFavoriteStateUseCaseImpl
import yegor.cheprasov.pokedex.features.pokemon.use_cases.GetPokemonEvolutionChainUseCase
import yegor.cheprasov.pokedex.features.pokemon.use_cases.GetPokemonUseCase
import yegor.cheprasov.pokedex.features.pokemon.use_cases.HasPokemonsInDatabaseUseCase
import yegor.cheprasov.pokedex.features.pokemon.use_cases.ObserveAllPokemonsUseCase
import yegor.cheprasov.pokedex.features.pokemon.use_cases.ObservePokemonFavoriteStateUseCase
import yegor.cheprasov.pokedex.features.pokemon.use_cases.SyncPokemonEvolutionsUseCase
import yegor.cheprasov.pokedex.features.pokemon.use_cases.SyncPokemonsUseCase
import yegor.cheprasov.pokedex.features.pokemon.use_cases.UpdatePokemonFavoriteStateUseCase
import yegor.cheprasov.pokedex.features.sync.data.api.SyncDataUseCase

val pokemonDomainModule: Module = module {
    factory<GetPokemonUseCase> {
        GetPokemonUseCaseImpl(get())
    }

    factory<GetPokemonEvolutionChainUseCase> {
        GetPokemonEvolutionChainUseCaseImpl(get())
    }

    factoryOf(::SyncPokemonsUseCaseImpl) {
        bind<SyncPokemonsUseCase>()
        bind<SyncDataUseCase>()
    }

    factoryOf(::SyncPokemonEvolutionsUseCaseImpl) {
        bind<SyncPokemonEvolutionsUseCase>()
        bind<SyncDataUseCase>()
    }

    factory<HasPokemonsInDatabaseUseCase> {
        HasPokemonsInDatabaseUseCaseImpl(get())
    }

    factory<ObserveAllPokemonsUseCase> {
        ObserveAllPokemonsUseCaseImpl(get())
    }

    factory<ObservePokemonFavoriteStateUseCase> {
        ObservePokemonFavoriteStateUseCaseImpl(get())
    }

    factory<UpdatePokemonFavoriteStateUseCase> {
        UpdatePokemonFavoriteStateUseCaseImpl(get())
    }
}
