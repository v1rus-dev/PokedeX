package yegor.cheprasov.pokedex.features.settings.presentation

import yegor.cheprasov.pokedex.core.design.mvi.ActionUi
import yegor.cheprasov.pokedex.core.design.mvi.EventUi
import yegor.cheprasov.pokedex.core.design.mvi.StateUi

data object SettingsStateUi : StateUi

sealed interface SettingsActionUi : ActionUi

sealed interface SettingsEventUi : EventUi
