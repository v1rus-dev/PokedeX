package yegor.cheprasov.pokedex.features.favorites.presentation.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import yegor.cheprasov.pokedex.features.favorites.presentation.PokemonFavoritesViewModel

val favoritesPresentationModule = module {
    viewModelOf(::PokemonFavoritesViewModel)
}
