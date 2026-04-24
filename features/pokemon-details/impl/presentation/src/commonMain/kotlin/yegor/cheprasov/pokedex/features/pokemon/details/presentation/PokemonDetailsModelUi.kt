package yegor.cheprasov.pokedex.features.pokemon.details.presentation

import yegor.cheprasov.pokedex.core.design.mvi.ActionUi
import yegor.cheprasov.pokedex.core.design.mvi.EventUi
import yegor.cheprasov.pokedex.core.design.mvi.StateUi
import yegor.cheprasov.pokedex.features.pokemon.ui.models.PokemonTypeUiModel

data class PokemonDetailsStateUi(
    val pokemonName: String,
    val pokemonType: PokemonTypeUiModel
) : StateUi {
    companion object {
        val PREVIEW = PokemonDetailsStateUi(
            pokemonName = "Bulbasaur",
            pokemonType = PokemonTypeUiModel.Grass
        )
    }
}

sealed interface PokemonDetailsActionUi : ActionUi {
    data object OnBackClick : PokemonDetailsActionUi
}

sealed interface PokemonDetailsEventUi : EventUi {
    data object CloseScreen : PokemonDetailsEventUi
}