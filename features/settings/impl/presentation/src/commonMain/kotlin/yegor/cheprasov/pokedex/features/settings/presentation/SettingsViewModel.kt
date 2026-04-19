package yegor.cheprasov.pokedex.features.settings.presentation

import yegor.cheprasov.pokedex.core.design.mvi.MviViewModel

class SettingsViewModel :
    MviViewModel<SettingsStateUi, SettingsActionUi, SettingsEventUi>(
        initialState = SettingsStateUi,
    ) {

    override fun onAction(action: SettingsActionUi) = Unit
}
