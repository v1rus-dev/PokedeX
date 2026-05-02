package yegor.cheprasov.pokedex.features.pokemon.details.presentation

import io.github.v1rusdev.simplemvi.core.EffectUi
import io.github.v1rusdev.simplemvi.core.IntentUi
import io.github.v1rusdev.simplemvi.core.StateUi
import yegor.cheprasov.pokedex.features.pokemon.ui.models.PokemonStatValueUiModel
import yegor.cheprasov.pokedex.features.pokemon.ui.models.PokemonStatsUiModel
import yegor.cheprasov.pokedex.features.pokemon.ui.models.PokemonTypeUiModel
import yegor.cheprasov.pokedex.features.pokemon.ui.models.PokemonUiModel
import yegor.cheprasov.pokedex.features.pokemon.ui.models.previewValueRange

data class PokemonDetailsStateUi(
    val pokemon: PokemonUiModel,
    val evolutions: List<PokemonUiModel> = emptyList(),
    val isFavorite: Boolean = false
) : StateUi {
    companion object {
        private val previewPokemon = PokemonUiModel(
            name = "Bulbasaur",
            id = 1,
            imageUrl = "",
            pokemonTypes = listOf(PokemonTypeUiModel.Grass, PokemonTypeUiModel.Poison),
            stats = PokemonStatsUiModel.entries
                .filterNot { it == PokemonStatsUiModel.Unknown }
                .map {
                    PokemonStatValueUiModel(
                        statsUiModel = it,
                        value = it.previewValueRange.random(),
                        minValue = it.previewValueRange.first,
                        maxValue = it.previewValueRange.last,
                    )
                }
        )

        val PREVIEW = PokemonDetailsStateUi(
            pokemon = previewPokemon,
            evolutions = listOf(
                previewPokemon,
                previewPokemon.copy(name = "Ivysaur", id = 2),
                previewPokemon.copy(name = "Venusaur", id = 3),
            ),
            isFavorite = false,
        )
    }
}

sealed interface PokemonDetailsLoadStateUi {
    data object Loading : PokemonDetailsLoadStateUi

    data class Success(
        val pokemon: PokemonUiModel,
    ) : PokemonDetailsLoadStateUi

    data object Failure : PokemonDetailsLoadStateUi
}

sealed interface PokemonDetailsIntentUi : IntentUi {
    data object OnFavoriteClick : PokemonDetailsIntentUi
    data object OnBackClick : PokemonDetailsIntentUi
}

sealed interface PokemonDetailsEffectUi : EffectUi {
    data object CloseScreen : PokemonDetailsEffectUi
}
