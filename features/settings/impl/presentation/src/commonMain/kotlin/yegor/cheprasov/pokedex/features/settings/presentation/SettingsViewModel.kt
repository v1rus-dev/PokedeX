package yegor.cheprasov.pokedex.features.settings.presentation

import androidx.lifecycle.viewModelScope
import io.github.v1rusdev.simplemvi.compose.MviViewModel
import kotlinx.coroutines.launch
import yegor.cheprasov.pokedex.features.settings.domain.use_cases.ObserveThemeModeUseCase
import yegor.cheprasov.pokedex.features.settings.domain.use_cases.SetThemeModeUseCase

class SettingsViewModel(
    private val observeThemeModeUseCase: ObserveThemeModeUseCase,
    private val setThemeModeUseCase: SetThemeModeUseCase,
) : MviViewModel<SettingsStateUi, SettingsIntentUi, SettingsEffectUi>(
        initialState = SettingsStateUi(),
    ) {

    init {
        viewModelScope.launch {
            observeThemeModeUseCase().collect { themeMode ->
                updateState { copy(themeMode = themeMode) }
            }
        }
    }

    override fun onIntent(intent: SettingsIntentUi) {
        when (intent) {
            is SettingsIntentUi.SelectThemeMode -> {
                viewModelScope.launch {
                    setThemeModeUseCase(intent.themeMode)
                }
            }
        }
    }
}
