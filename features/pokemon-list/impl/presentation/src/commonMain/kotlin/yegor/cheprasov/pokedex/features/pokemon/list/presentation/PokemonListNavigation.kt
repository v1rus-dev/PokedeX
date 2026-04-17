package yegor.cheprasov.pokedex.features.pokemon.list.presentation

import yegor.cheprasov.pokedex.core.design.navigation.AppNavigator
import yegor.cheprasov.pokedex.core.design.navigation.TopLevelDestinationSpec
import yegor.cheprasov.pokedex.features.pokemon.list.api.PokemonList
import yegor.cheprasov.pokedex.features.pokemon.details.api.PokemonDetails

val pokemonListTopLevelDestination = TopLevelDestinationSpec(
    route = PokemonList,
    label = "Pokedex",
    iconLabel = "Dx",
    content = { navigator: AppNavigator ->
        PokemonListDestination(
            onOpenPokemon = { name ->
                navigator.navigate(PokemonDetails(pokemonName = name))
            },
        )
    },
)
