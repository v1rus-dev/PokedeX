package yegor.cheprasov.pokedex.features.pokemon.data.di

import org.koin.core.module.Module
import org.koin.dsl.module
import yegor.cheprasov.pokedex.features.pokemon.data.datasource.NetworkPokemonDatasource
import yegor.cheprasov.pokedex.features.pokemon.data.mapper.PokemonEntityMapper
import yegor.cheprasov.pokedex.features.pokemon.data.mapper.PokemonResponseMapper
import yegor.cheprasov.pokedex.features.pokemon.data.repository_impl.PokemonRepositoryImpl
import yegor.cheprasov.pokedex.features.pokemon.domain.repository.PokemonRepository

val pokemonDataModule: Module = module {
    single<NetworkPokemonDatasource> {
        NetworkPokemonDatasource(get())
    }

    factory { PokemonResponseMapper() }
    factory { PokemonEntityMapper() }

    single<PokemonRepository> {
        PokemonRepositoryImpl(
            pokemonDao = get(),
            datasource = get(),
            pokemonResponseMapper = get(),
            pokemonEntityMapper = get(),
        )
    }
}
