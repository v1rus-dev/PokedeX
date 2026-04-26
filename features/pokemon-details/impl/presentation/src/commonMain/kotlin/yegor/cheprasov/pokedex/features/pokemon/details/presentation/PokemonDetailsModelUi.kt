package yegor.cheprasov.pokedex.features.pokemon.details.presentation

import io.github.v1rusdev.simplemvi.core.EffectUi
import io.github.v1rusdev.simplemvi.core.IntentUi
import io.github.v1rusdev.simplemvi.core.StateUi
import yegor.cheprasov.pokedex.features.pokemon.ui.models.PokemonTypeUiModel
import yegor.cheprasov.pokedex.features.pokemon.ui.models.PokemonUiModel

data class PokemonDetailsStateUi(
    val pokemonName: String,
    val pokemonType: PokemonTypeUiModel,
    val isFavorite: Boolean = false,
    val detailsState: PokemonDetailsLoadStateUi = PokemonDetailsLoadStateUi.Loading
) : StateUi {
    companion object {
        val PREVIEW = PokemonDetailsStateUi(
            pokemonName = "Bulbasaur",
            pokemonType = PokemonTypeUiModel.Grass,
            isFavorite = false,
            detailsState = PokemonDetailsLoadStateUi.Success(
                pokemon = PokemonUiModel(
                    name = "Bulbasaur",
                    id = 1,
                    imageUrl = "",
                    pokemonTypes = listOf(PokemonTypeUiModel.Grass, PokemonTypeUiModel.Poison)
                )
            )
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
