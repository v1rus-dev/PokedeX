package yegor.cheprasov.pokedex.features.home.presentation

import androidx.lifecycle.viewModelScope
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import yegor.cheprasov.pokedex.core.common.mapper.Mapper
import yegor.cheprasov.pokedex.core.design.mvi.MviViewModel
import yegor.cheprasov.pokedex.features.home.presentation.models.SyncAllPokemonsStateModelUi
import yegor.cheprasov.pokedex.features.pokemon.models.SyncAllPokemonsState
import yegor.cheprasov.pokedex.features.pokemon.use_cases.HasPokemonsInDatabaseUseCase
import yegor.cheprasov.pokedex.features.pokemon.use_cases.SyncPokemonsUseCase

class HomeViewModel(
    private val hasPokemonsInDatabaseUseCase: HasPokemonsInDatabaseUseCase,
    private val syncPokemonsUseCase: SyncPokemonsUseCase,
    private val syncAllPokemonsStateToModelUiMapper: Mapper<SyncAllPokemonsState, SyncAllPokemonsStateModelUi>
) : MviViewModel<HomeStateUi, HomeActionUi, HomeEventUi>(
    initialState = HomeStateUi(),
) {

    init {
        syncPokemons()
    }


    private fun syncPokemons(checkDatabase: Boolean = true) {
        viewModelScope.launch {
            if (checkDatabase && hasPokemonsInDatabaseUseCase()) return@launch
            syncPokemonsUseCase().map(syncAllPokemonsStateToModelUiMapper::map)
                .collectLatest { state ->
                    Napier.d("Sync pokemon use case: $state", tag = "myTag")
                    updateState { copy(syncAllPokemonsStateModelUi = state) }
                }
        }
    }

    override fun onAction(action: HomeActionUi) {
        when (action) {
            HomeActionUi.OnSearchClick -> sendEvent(HomeEventUi.OpenSearchScreen)
            HomeActionUi.OnRefreshPokemons -> syncPokemons(false)
        }
    }
}
