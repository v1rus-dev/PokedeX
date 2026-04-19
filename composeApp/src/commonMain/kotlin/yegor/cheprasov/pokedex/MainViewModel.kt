package yegor.cheprasov.pokedex

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import yegor.cheprasov.pokedex.core.design.mvi.MviViewModel
import yegor.cheprasov.pokedex.features.settings.api.ThemeMode
import yegor.cheprasov.pokedex.features.settings.domain.use_cases.ObserveThemeModeUseCase

class MainViewModel(
    private val observeThemeModeUseCase: ObserveThemeModeUseCase
) : MviViewModel<MainStateUi, MainActionUi, MainEventUi>(initialState = MainStateUi(ThemeMode.System)) {

    init {
        observeTheme()
    }

    private fun observeTheme() {
        viewModelScope.launch {
            observeThemeModeUseCase()
                .collectLatest { themeMode ->
                    updateState { copy(themeMode = themeMode) }
                }
        }
    }
}