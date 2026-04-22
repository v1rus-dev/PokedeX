package yegor.cheprasov.pokedex.features.pokemon.data.di

import org.koin.core.module.Module
import org.koin.dsl.module
import yegor.cheprasov.pokedex.features.ability.data.datasource.LocalAbilityDatasource
import yegor.cheprasov.pokedex.features.ability.data.datasource.NetworkAbilityDatasource
import yegor.cheprasov.pokedex.features.ability.data.mapper.AbilityResponseMapper
import yegor.cheprasov.pokedex.features.pokemon.data.datasource.LocalPokemonDatasource
import yegor.cheprasov.pokedex.features.pokemon.data.datasource.NetworkPokemonDatasource
import yegor.cheprasov.pokedex.features.pokemon.data.mapper.PokemonEntityMapper
import yegor.cheprasov.pokedex.features.pokemon.data.mapper.PokemonResponseMapper
import yegor.cheprasov.pokedex.features.pokemon.data.repositories.PokemonRepositoryImpl
import yegor.cheprasov.pokedex.features.pokemon.domain.repository.PokemonRepository

val pokemonDataModule: Module = module {
    single<NetworkPokemonDatasource> {
        NetworkPokemonDatasource(get())
    }

    single<LocalPokemonDatasource> {
        LocalPokemonDatasource(get(), get(), get(), get())
    }

    factory { PokemonResponseMapper() }
    factory { PokemonEntityMapper() }

    single<PokemonRepository> {
        PokemonRepositoryImpl(
            networkDatasource = get(),
            localDatasource = get(),
            pokemonResponseMapper = get(),
            pokemonEntityMapper = get(),
            abilityNetworkDatasource = get<NetworkAbilityDatasource>(),
            abilityResponseMapper = get<AbilityResponseMapper>(),
            localAbilityDatasource = get<LocalAbilityDatasource>(),
        )
    }
}
