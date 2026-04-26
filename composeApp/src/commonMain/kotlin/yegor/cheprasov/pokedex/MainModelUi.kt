package yegor.cheprasov.pokedex

import io.github.v1rusdev.simplemvi.core.EffectUi
import io.github.v1rusdev.simplemvi.core.IntentUi
import io.github.v1rusdev.simplemvi.core.StateUi
import yegor.cheprasov.pokedex.features.settings.api.ThemeMode

data class MainStateUi(
    val themeMode: ThemeMode
) : StateUi

sealed interface MainIntentUi : IntentUi

sealed interface MainEffectUi : EffectUi