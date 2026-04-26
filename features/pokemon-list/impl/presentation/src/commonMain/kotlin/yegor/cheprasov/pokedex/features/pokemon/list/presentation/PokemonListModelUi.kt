package yegor.cheprasov.pokedex.features.pokemon.list.presentation

import io.github.v1rusdev.simplemvi.core.EffectUi
import io.github.v1rusdev.simplemvi.core.IntentUi
import io.github.v1rusdev.simplemvi.core.StateUi
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonType
import yegor.cheprasov.pokedex.features.pokemon.ui.models.PokemonUiModel

data object PokemonListStateUi : StateUi

sealed interface PokemonListIntentUi : IntentUi {
    data object OnBackClick : PokemonListIntentUi

    data class SearchQueryChanged(
        val query: String,
    ) : PokemonListIntentUi

    data class ClickPokemon(
        val pokemonUiModel: PokemonUiModel
    ) : PokemonListIntentUi
}

sealed interface PokemonListEffectUi : EffectUi {
    data object CloseScreen : PokemonListEffectUi

    data class NavigateToPokemonDetail(
        val name: String,
        val pokemonType: PokemonType
    ) : PokemonListEffectUi
}
