package yegor.cheprasov.pokedex.features.pokemon.details.presentation

import androidx.lifecycle.viewModelScope
import io.github.aakira.napier.Napier
import io.github.v1rusdev.simplemvi.compose.MviViewModel
import kotlinx.coroutines.launch
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonType
import yegor.cheprasov.pokedex.features.pokemon.ui.mappers.PokemonModelToUiModelMapper
import yegor.cheprasov.pokedex.features.pokemon.ui.mappers.PokemonTypeModelToUiModel
import yegor.cheprasov.pokedex.features.pokemon.ui.mappers.PokemonTypeUiModelToModel
import yegor.cheprasov.pokedex.features.pokemon.ui.models.PokemonUiModel
import yegor.cheprasov.pokedex.features.pokemon.use_cases.GetPokemonEvolutionChainUseCase
import yegor.cheprasov.pokedex.features.pokemon.use_cases.GetPokemonUseCase
import yegor.cheprasov.pokedex.features.pokemon.use_cases.ObservePokemonFavoriteStateUseCase
import yegor.cheprasov.pokedex.features.pokemon.use_cases.UpdatePokemonFavoriteStateUseCase

class PokemonDetailsViewModel(
    private val pokemonName: String,
    private val pokemonType: PokemonType,
    private val getPokemonUseCase: GetPokemonUseCase,
    private val getPokemonEvolutionChainUseCase: GetPokemonEvolutionChainUseCase,
    private val observePokemonFavoriteStateUseCase: ObservePokemonFavoriteStateUseCase,
    private val updatePokemonFavoriteStateUseCase: UpdatePokemonFavoriteStateUseCase,
    private val pokemonModelToUiModelMapper: PokemonModelToUiModelMapper,
    private val pokemonTypeMapper: PokemonTypeModelToUiModel,
    private val pokemonTypeUiModelToModel: PokemonTypeUiModelToModel
) : MviViewModel<PokemonDetailsStateUi, PokemonDetailsIntentUi, PokemonDetailsEffectUi>(
    initialState = PokemonDetailsStateUi(
        pokemon = PokemonUiModel.fromNavArgs(pokemonName, pokemonTypeMapper.map(pokemonType)),
    )
) {

    init {
        getPokemon()
        getPokemonEvolutions()
        observeFavoriteState()
    }

    override fun onIntent(intent: PokemonDetailsIntentUi) {
        when (intent) {
            PokemonDetailsIntentUi.OnBackClick -> sendEffect(PokemonDetailsEffectUi.CloseScreen)
            is PokemonDetailsIntentUi.OnEvolutionClick -> onEvolutionClick(intent.pokemon)
            PokemonDetailsIntentUi.OnFavoriteClick -> onFavoriteClick()
        }
    }

    private fun getPokemon() {
        viewModelScope.launch {
            getPokemonUseCase.invoke(pokemonName)
                .map(pokemonModelToUiModelMapper::map)
                .onSuccess { pokemon ->
                    updateState {
                        copy(pokemon = pokemon)
                    }
                }
                .onFailure { throwable ->
                    Napier.v("Can't load pokemon: $throwable")
                }
        }
    }

    private fun getPokemonEvolutions() {
        viewModelScope.launch {
            getPokemonEvolutionChainUseCase(pokemonName)
                .map { evolutions ->
                    evolutions.map(pokemonModelToUiModelMapper::map)
                }
                .onSuccess { evolutions ->
                    updateState {
                        copy(evolutions = evolutions)
                    }
                }
                .onFailure { throwable ->
                    Napier.v("Can't load pokemon evolutions: $throwable")
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

    private fun onEvolutionClick(pokemon: PokemonUiModel) {
        if (pokemon.isCurrentPokemon()) {
            return
        }

        sendEffect(
            PokemonDetailsEffectUi.OpenPokemonDetails(
                pokemonName = pokemon.name,
                pokemonType = pokemonTypeUiModelToModel.map(pokemon.mainType)
            )
        )
    }

    private fun PokemonUiModel.isCurrentPokemon(): Boolean {
        val currentPokemon = uiState.value.pokemon
        return (id != 0 && id == currentPokemon.id) || name.equals(currentPokemon.name, ignoreCase = true)
    }
}
