package yegor.cheprasov.pokedex.features.home.presentation

import yegor.cheprasov.pokedex.core.design.mvi.ActionUi
import yegor.cheprasov.pokedex.core.design.mvi.EventUi
import yegor.cheprasov.pokedex.core.design.mvi.StateUi

data object HomeStateUi : StateUi

sealed interface HomeActionUi : ActionUi {
    data object OnSearchClick : HomeActionUi
}

sealed interface HomeEventUi : EventUi {
    data object OpenSearchScreen : HomeEventUi
}
