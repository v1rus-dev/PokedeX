package yegor.cheprasov.pokedex.features.settings.presentation

import io.github.v1rusdev.simplemvi.core.EffectUi
import io.github.v1rusdev.simplemvi.core.IntentUi
import io.github.v1rusdev.simplemvi.core.StateUi
import yegor.cheprasov.pokedex.features.settings.api.ThemeMode

data class SettingsStateUi(
    val themeMode: ThemeMode = ThemeMode.System,
) : StateUi

sealed interface SettingsIntentUi : IntentUi {
    data class SelectThemeMode(val themeMode: ThemeMode) : SettingsIntentUi
}

sealed interface SettingsEffectUi : EffectUi
