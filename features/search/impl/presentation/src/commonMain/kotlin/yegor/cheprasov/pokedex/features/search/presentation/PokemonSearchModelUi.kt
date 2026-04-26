package yegor.cheprasov.pokedex.features.search.presentation

import io.github.v1rusdev.simplemvi.core.EffectUi
import io.github.v1rusdev.simplemvi.core.IntentUi
import io.github.v1rusdev.simplemvi.core.StateUi


data object PokemonSearchStateUi : StateUi

sealed interface PokemonSearchIntentUi : IntentUi {
    data object OnBackClicked : PokemonSearchIntentUi
}

sealed interface PokemonSearchEffectUi : EffectUi {
    data object CloseScreen : PokemonSearchEffectUi
}
