package yegor.cheprasov.pokedex.features.favorites.presentation

import pokedex.core.resources.generated.resources.Res
import pokedex.core.resources.generated.resources.favorites
import yegor.cheprasov.pokedex.core.design.navigation.TopLevelDestinationSpec
import yegor.cheprasov.pokedex.features.favorites.api.Favorites

val favoritesTopLevelDestination = TopLevelDestinationSpec(
    route = Favorites,
    icon = Res.drawable.favorites,
    label = Res.string.favorites,
    content = { PokemonFavoritesDestination() },
)
