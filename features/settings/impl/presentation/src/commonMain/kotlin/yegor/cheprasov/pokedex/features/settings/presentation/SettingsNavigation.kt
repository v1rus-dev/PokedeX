package yegor.cheprasov.pokedex.features.settings.presentation

import pokedex.core.resources.generated.resources.Res
import pokedex.core.resources.generated.resources.pokeball_settings
import pokedex.core.resources.generated.resources.settings
import yegor.cheprasov.pokedex.core.design.navigation.AppNavigator
import yegor.cheprasov.pokedex.core.design.navigation.TopLevelDestinationSpec
import yegor.cheprasov.pokedex.features.settings.api.Settings

val settingsTopLevelNavigation = TopLevelDestinationSpec(
    route = Settings,
    icon = Res.drawable.pokeball_settings,
    label = Res.string.settings,
    content = { navigator: AppNavigator ->
        SettingsDestination()
    }
)