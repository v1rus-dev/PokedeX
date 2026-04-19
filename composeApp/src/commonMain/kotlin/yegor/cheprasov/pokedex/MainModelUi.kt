package yegor.cheprasov.pokedex

import yegor.cheprasov.pokedex.core.design.mvi.ActionUi
import yegor.cheprasov.pokedex.core.design.mvi.EventUi
import yegor.cheprasov.pokedex.core.design.mvi.StateUi
import yegor.cheprasov.pokedex.features.settings.api.ThemeMode

data class MainStateUi(
    val themeMode: ThemeMode
) : StateUi

sealed interface MainActionUi : ActionUi

sealed interface MainEventUi : EventUi