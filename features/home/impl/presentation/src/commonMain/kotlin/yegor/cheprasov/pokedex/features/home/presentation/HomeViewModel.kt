package yegor.cheprasov.pokedex.features.home.presentation

import androidx.lifecycle.viewModelScope
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import yegor.cheprasov.pokedex.core.common.mapper.Mapper
import yegor.cheprasov.pokedex.core.design.mvi.MviViewModel
import yegor.cheprasov.pokedex.features.home.domain.use_cases.SyncUseCase
import yegor.cheprasov.pokedex.features.home.presentation.models.HomeMainCardTypeUi
import yegor.cheprasov.pokedex.features.home.presentation.models.SyncAllPokemonsStateModelUi
import yegor.cheprasov.pokedex.features.sync.data.api.SyncDataKey
import yegor.cheprasov.pokedex.features.sync.data.api.SyncDataState

class HomeViewModel(
    private val syncUseCase: SyncUseCase,
    private val syncStateToModelUiMapper: Mapper<SyncDataState, SyncAllPokemonsStateModelUi>,
) : MviViewModel<HomeStateUi, HomeActionUi, HomeEventUi>(
    initialState = HomeStateUi(),
) {

    init {
        syncInitialData()
    }

    override fun onAction(action: HomeActionUi) {
        when (action) {
            HomeActionUi.OnSearchClick -> sendEvent(HomeEventUi.OpenSearchScreen)
            HomeActionUi.OnRefreshPokemons -> refreshData()
            HomeActionUi.OnSeeMorePokemonClick -> Unit
            is HomeActionUi.OnClickMainHomeCard -> onMainHomeCardClick(action.type)
        }
    }

    private fun onMainHomeCardClick(type: HomeMainCardTypeUi) {
        when (type) {
            HomeMainCardTypeUi.POKEMONS -> sendEvent(HomeEventUi.OpenPokemonListScreen)
            HomeMainCardTypeUi.ABILITIES -> Unit
            HomeMainCardTypeUi.LOCATIONS -> Unit
        }
    }

    private fun syncInitialData() {
        refreshData(force = false)
    }

    private fun refreshData() {
        refreshData(force = true)
    }

    private fun refreshData(force: Boolean) {
        viewModelScope.launch {
            syncUseCase(force = force)
                .mapNotNull { states -> states[SyncDataKey.POKEMONS] }
                .mapNotNull { state ->
                    state.takeUnless { it is SyncDataState.Skipped }
                }
                .distinctUntilChanged()
                .map(syncStateToModelUiMapper::map)
                .collectLatest { state ->
                    Napier.d("Sync pokemon use case: $state", tag = "home-sync")
                    updateState { copy(syncAllPokemonsStateModelUi = state) }
                }
        }
    }
}
