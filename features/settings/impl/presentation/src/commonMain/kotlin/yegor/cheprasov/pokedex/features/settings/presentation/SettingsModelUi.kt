package yegor.cheprasov.pokedex.features.settings.presentation

import yegor.cheprasov.pokedex.core.design.mvi.ActionUi
import yegor.cheprasov.pokedex.core.design.mvi.EventUi
import yegor.cheprasov.pokedex.core.design.mvi.StateUi
import yegor.cheprasov.pokedex.features.settings.api.ThemeMode

data class SettingsStateUi(
    val themeMode: ThemeMode = ThemeMode.System,
) : StateUi

sealed interface SettingsActionUi : ActionUi {
    data class SelectThemeMode(val themeMode: ThemeMode) : SettingsActionUi
}

sealed interface SettingsEventUi : EventUi
