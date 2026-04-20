package yegor.cheprasov.pokedex.features.pokemon.details.presentation.di

import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.koin.dsl.navigation3.navigation
import yegor.cheprasov.pokedex.core.design.animation.ProvideLocalAnimatedScope
import yegor.cheprasov.pokedex.features.pokemon.details.api.PokemonDetails
import yegor.cheprasov.pokedex.features.pokemon.details.presentation.PokemonDetailsDestination
import yegor.cheprasov.pokedex.features.pokemon.details.presentation.PokemonDetailsViewModel

@OptIn(KoinExperimentalAPI::class)
val pokemonDetailsPresentationModule = module {
    viewModelOf(::PokemonDetailsViewModel)

    navigation<PokemonDetails> { route ->
        ProvideLocalAnimatedScope {
            PokemonDetailsDestination()
        }
    }
}