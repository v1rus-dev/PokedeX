package yegor.cheprasov.pokedex.features.pokemon.list.data.di

import org.koin.dsl.module
import yegor.cheprasov.pokedex.features.pokemon.list.data.datasource.LocalPokemonListDatasource
import yegor.cheprasov.pokedex.features.pokemon.list.data.mappers.PokemonMapper
import yegor.cheprasov.pokedex.features.pokemon.list.data.paging.PokemonPagingSource
import yegor.cheprasov.pokedex.features.pokemon.list.data.repositories_impl.PokemonListRepositoryImpl
import yegor.cheprasov.pokedex.features.pokemon.list.domain.repositories.PokemonListRepository

val pokemonListDataModule = module {
    single<PokemonListRepository> {
        PokemonListRepositoryImpl(
            pagingSourceFactory = { searchQuery ->
                PokemonPagingSource(
                    localPokemonListDatasource = get(),
                    pokemonMapper = get(),
                    searchQuery = searchQuery,
                )
            }
        )
    }

    single { LocalPokemonListDatasource(get()) }
    factory { PokemonMapper() }
}
