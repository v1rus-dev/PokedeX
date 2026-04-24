package yegor.cheprasov.pokedex.features.pokemon.details.presentation.di

import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.koin.dsl.navigation3.navigation
import org.koin.plugin.module.dsl.viewModel
import yegor.cheprasov.pokedex.core.design.animation.ProvideLocalAnimatedScope
import yegor.cheprasov.pokedex.features.pokemon.details.api.PokemonDetails
import yegor.cheprasov.pokedex.features.pokemon.details.presentation.PokemonDetailsDestination
import yegor.cheprasov.pokedex.features.pokemon.details.presentation.PokemonDetailsViewModel

@OptIn(KoinExperimentalAPI::class)
val pokemonDetailsPresentationModule = module {
    viewModel<PokemonDetailsViewModel> { params ->
        PokemonDetailsViewModel(
            pokemonName = params[0],
            pokemonType = params[1],
            getPokemonUseCase = get(),
            pokemonModelToUiModelMapper = get(),
            pokemonTypeMapper = get()
        )
    }

    navigation<PokemonDetails> { route ->
        ProvideLocalAnimatedScope {
            PokemonDetailsDestination(route.pokemonName, route.pokemonType)
        }
    }
}