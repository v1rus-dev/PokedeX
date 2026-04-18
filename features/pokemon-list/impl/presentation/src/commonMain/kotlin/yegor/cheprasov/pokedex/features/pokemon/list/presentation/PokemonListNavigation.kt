package yegor.cheprasov.pokedex.features.pokemon.list.presentation

import pokedex.core.resources.generated.resources.Res
import pokedex.core.resources.generated.resources.home
import pokedex.core.resources.generated.resources.pokeball
import yegor.cheprasov.pokedex.core.design.navigation.AppNavigator
import yegor.cheprasov.pokedex.core.design.navigation.TopLevelDestinationSpec
import yegor.cheprasov.pokedex.features.pokemon.list.api.PokemonList
import yegor.cheprasov.pokedex.features.pokemon.details.api.PokemonDetails

val pokemonListTopLevelDestination = TopLevelDestinationSpec(
    route = PokemonList,
    icon = Res.drawable.pokeball,
    label = Res.string.home,
    content = { navigator: AppNavigator ->
        PokemonListDestination(
            onOpenPokemon = { name ->
                navigator.navigate(PokemonDetails(pokemonName = name))
            },
        )
    },
)
