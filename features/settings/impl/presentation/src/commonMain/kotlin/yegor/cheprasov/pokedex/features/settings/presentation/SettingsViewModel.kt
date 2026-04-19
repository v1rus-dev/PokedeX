package yegor.cheprasov.pokedex.features.settings.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import yegor.cheprasov.pokedex.core.design.mvi.MviViewModel
import yegor.cheprasov.pokedex.features.settings.domain.use_cases.ObserveThemeModeUseCase
import yegor.cheprasov.pokedex.features.settings.domain.use_cases.SetThemeModeUseCase

class SettingsViewModel(
    private val observeThemeModeUseCase: ObserveThemeModeUseCase,
    private val setThemeModeUseCase: SetThemeModeUseCase,
) :
    MviViewModel<SettingsStateUi, SettingsActionUi, SettingsEventUi>(
        initialState = SettingsStateUi(),
    ) {

    init {
        viewModelScope.launch {
            observeThemeModeUseCase().collect { themeMode ->
                updateState { copy(themeMode = themeMode) }
            }
        }
    }

    override fun onAction(action: SettingsActionUi) {
        when (action) {
            is SettingsActionUi.SelectThemeMode -> {
                viewModelScope.launch {
                    setThemeModeUseCase(action.themeMode)
                }
            }
        }
    }
}
