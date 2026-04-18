package yegor.cheprasov.pokedex.features.pokemon.data.di

import org.koin.core.module.Module
import org.koin.dsl.module
import yegor.cheprasov.pokedex.features.pokemon.api.GetPokemonUseCase
import yegor.cheprasov.pokedex.features.pokemon.data.GetPokemonUseCaseImpl
import yegor.cheprasov.pokedex.features.pokemon.data.repository_impl.PokemonRepositoryImpl
import yegor.cheprasov.pokedex.features.pokemon.domain.repository.PokemonRepository

val pokemonDataModule: Module = module {
    single<PokemonRepository> {
        PokemonRepositoryImpl()
    }

    factory<GetPokemonUseCase> {
        GetPokemonUseCaseImpl()
    }
}