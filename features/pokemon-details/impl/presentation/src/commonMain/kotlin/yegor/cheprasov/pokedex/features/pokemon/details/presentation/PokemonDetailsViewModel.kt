package yegor.cheprasov.pokedex.features.pokemon.details.presentation

import androidx.lifecycle.viewModelScope
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch
import yegor.cheprasov.pokedex.core.design.mvi.MviViewModel
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonType
import yegor.cheprasov.pokedex.features.pokemon.ui.mappers.PokemonModelToUiModelMapper
import yegor.cheprasov.pokedex.features.pokemon.ui.mappers.PokemonTypeModelToUiModel
import yegor.cheprasov.pokedex.features.pokemon.use_cases.GetPokemonUseCase

class PokemonDetailsViewModel(
    private val pokemonName: String,
    private val pokemonType: PokemonType,
    private val getPokemonUseCase: GetPokemonUseCase,
    private val pokemonModelToUiModelMapper: PokemonModelToUiModelMapper,
    private val pokemonTypeMapper: PokemonTypeModelToUiModel
) : MviViewModel<PokemonDetailsStateUi, PokemonDetailsActionUi, PokemonDetailsEventUi>(
    initialState = PokemonDetailsStateUi(
        pokemonName = pokemonName,
        pokemonType = pokemonTypeMapper.map(pokemonType)
    )
) {

    init {
        getPokemon()
    }

    override fun onAction(action: PokemonDetailsActionUi) {
        when(action) {
            PokemonDetailsActionUi.OnBackClick -> sendEvent(PokemonDetailsEventUi.CloseScreen)
        }
    }

    private fun getPokemon() {
        viewModelScope.launch {
            getPokemonUseCase.invoke(pokemonName)
                .map(pokemonModelToUiModelMapper::map)
                .onSuccess {
                    Napier.v("Pokemon: ${it}")
                }
                .onFailure {
                    Napier.e("Failure getting pokemon: $it")
                }
        }
    }

}