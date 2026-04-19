package yegor.cheprasov.pokedex.features.home.presentation

import pokedex.core.resources.generated.resources.Res
import pokedex.core.resources.generated.resources.home
import pokedex.core.resources.generated.resources.pokeball
import yegor.cheprasov.pokedex.core.design.navigation.TopLevelDestinationSpec
import yegor.cheprasov.pokedex.features.home.api.Home

val homeTopLevelDestination = TopLevelDestinationSpec(
    route = Home,
    icon = Res.drawable.pokeball,
    label = Res.string.home,
    content = { HomeDestination() },
)
