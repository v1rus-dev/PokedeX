package yegor.cheprasov.pokedex.features.search.presentation

import io.github.v1rusdev.simplemvi.compose.MviViewModel

class PokemonSearchViewModel :
    MviViewModel<PokemonSearchStateUi, PokemonSearchIntentUi, PokemonSearchEffectUi>(
        initialState = PokemonSearchStateUi,
    ) {
    override fun onIntent(intent: PokemonSearchIntentUi) {
        when (intent) {
            PokemonSearchIntentUi.OnBackClicked -> sendEffect(PokemonSearchEffectUi.CloseScreen)
        }
    }
}
