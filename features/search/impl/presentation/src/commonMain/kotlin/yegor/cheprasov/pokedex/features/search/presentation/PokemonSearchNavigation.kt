package yegor.cheprasov.pokedex.features.search.presentation

import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.koin.dsl.navigation3.navigation
import yegor.cheprasov.pokedex.core.design.animation.ProvideLocalAnimatedScope
import yegor.cheprasov.pokedex.core.design.navigation.AppNavigator
import yegor.cheprasov.pokedex.features.search.api.PokemonSearch

@OptIn(KoinExperimentalAPI::class)
val pokemonSearchPresentationModule = module {
    viewModelOf(::PokemonSearchViewModel)

    navigation<PokemonSearch> {
        ProvideLocalAnimatedScope {
            PokemonSearchDestination()
        }
    }
}
