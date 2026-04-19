package yegor.cheprasov.pokedex.features.pokemon.list.data.di

import org.koin.dsl.module
import yegor.cheprasov.pokedex.features.pokemon.list.data.datasource.NetworkPokemonListDatasource
import yegor.cheprasov.pokedex.features.pokemon.list.data.mappers.PokemonMapper
import yegor.cheprasov.pokedex.features.pokemon.list.data.paging.PokemonListPagingSource
import yegor.cheprasov.pokedex.features.pokemon.list.data.repositories_impl.PokemonListRepositoryImpl
import yegor.cheprasov.pokedex.features.pokemon.list.domain.repositories.PokemonListRepository

val pokemonListDataModule = module {
    single<PokemonListRepository> {
        PokemonListRepositoryImpl(
            pagingSourceFactory = { get<PokemonListPagingSource>() }
        )
    }

    single { NetworkPokemonListDatasource(get()) }
    factory { PokemonListPagingSource(get(), get()) }
    factory { PokemonMapper() }
}
