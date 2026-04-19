package yegor.cheprasov.pokedex.features.home.presentation

import yegor.cheprasov.pokedex.core.design.mvi.MviViewModel

class HomeViewModel :
    MviViewModel<HomeStateUi, HomeActionUi, HomeEventUi>(
        initialState = HomeStateUi,
    ) {

    override fun onAction(action: HomeActionUi) = Unit
}
