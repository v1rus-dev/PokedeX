package yegor.cheprasov.pokedex.features.pokemon.details.presentation

import yegor.cheprasov.pokedex.core.design.mvi.MviViewModel

class PokemonDetailsViewModel : MviViewModel<PokemonDetailsStateUi, PokemonDetailsActionUi, PokemonDetailsEventUi>(
    initialState = PokemonDetailsStateUi
) {
}