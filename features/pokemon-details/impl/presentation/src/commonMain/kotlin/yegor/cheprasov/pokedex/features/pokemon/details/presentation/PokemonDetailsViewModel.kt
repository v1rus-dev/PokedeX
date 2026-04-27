package yegor.cheprasov.pokedex.features.pokemon.details.presentation

import androidx.lifecycle.viewModelScope
import io.github.aakira.napier.Napier
import io.github.v1rusdev.simplemvi.compose.MviViewModel
import kotlinx.coroutines.launch
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonType
import yegor.cheprasov.pokedex.features.pokemon.ui.mappers.PokemonModelToUiModelMapper
import yegor.cheprasov.pokedex.features.pokemon.ui.mappers.PokemonTypeModelToUiModel
import yegor.cheprasov.pokedex.features.pokemon.ui.models.PokemonUiModel
import yegor.cheprasov.pokedex.features.pokemon.use_cases.GetPokemonUseCase
import yegor.cheprasov.pokedex.features.pokemon.use_cases.ObservePokemonFavoriteStateUseCase
import yegor.cheprasov.pokedex.features.pokemon.use_cases.UpdatePokemonFavoriteStateUseCase

class PokemonDetailsViewModel(
    private val pokemonName: String,
    private val pokemonType: PokemonType,
    private val getPokemonUseCase: GetPokemonUseCase,
    private val observePokemonFavoriteStateUseCase: ObservePokemonFavoriteStateUseCase,
    private val updatePokemonFavoriteStateUseCase: UpdatePokemonFavoriteStateUseCase,
    private val pokemonModelToUiModelMapper: PokemonModelToUiModelMapper,
    private val pokemonTypeMapper: PokemonTypeModelToUiModel
) : MviViewModel<PokemonDetailsStateUi, PokemonDetailsIntentUi, PokemonDetailsEffectUi>(
    initialState = PokemonDetailsStateUi(
        pokemon = PokemonUiModel.fromNavArgs(pokemonName, pokemonTypeMapper.map(pokemonType)),
    )
) {

    init {
        getPokemon()
        observeFavoriteState()
    }

    override fun onIntent(intent: PokemonDetailsIntentUi) {
        when (intent) {
            PokemonDetailsIntentUi.OnBackClick -> sendEffect(PokemonDetailsEffectUi.CloseScreen)
            PokemonDetailsIntentUi.OnFavoriteClick -> onFavoriteClick()
        }
    }

    private fun getPokemon() {
        viewModelScope.launch {
            getPokemonUseCase.invoke(pokemonName)
                .map(pokemonModelToUiModelMapper::map)
                .onSuccess { pokemon ->
                    Napier.v("Pokemon: $pokemon")
                    updateState {
                        copy(pokemon = pokemon)
                    }
                }
                .onFailure { throwable ->

                }
        }
    }

    private fun observeFavoriteState() {
        viewModelScope.launch {
            observePokemonFavoriteStateUseCase(pokemonName)
                .collect { isFavorite ->
                    updateState { copy(isFavorite = isFavorite) }
                }
        }
    }

    private fun onFavoriteClick() {
        viewModelScope.launch {
            updatePokemonFavoriteStateUseCase(pokemonName, isFavorite = !uiState.value.isFavorite)
        }
    }
}
