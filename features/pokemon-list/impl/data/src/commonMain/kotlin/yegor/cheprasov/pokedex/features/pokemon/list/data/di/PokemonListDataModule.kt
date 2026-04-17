package yegor.cheprasov.pokedex.features.pokemon.list.data.di

import org.koin.dsl.module
import yegor.cheprasov.pokedex.features.pokemon.list.data.repositories_impl.PokemonListRepositoryImpl
import yegor.cheprasov.pokedex.features.pokemon.list.domain.repositories.PokemonListRepository

val pokemonListDataModule = module {
    single<PokemonListRepository> { PokemonListRepositoryImpl(get()) }
}
