package yegor.cheprasov.pokedex.features.home.presentation

import androidx.lifecycle.viewModelScope
import io.github.aakira.napier.Napier
import io.github.v1rusdev.simplemvi.compose.MviViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import yegor.cheprasov.pokedex.core.common.mapper.Mapper
import yegor.cheprasov.pokedex.features.home.domain.use_cases.SyncUseCase
import yegor.cheprasov.pokedex.features.home.presentation.models.HomeMainCardTypeUi
import yegor.cheprasov.pokedex.features.home.presentation.models.SyncAllPokemonsStateModelUi
import yegor.cheprasov.pokedex.features.sync.data.api.SyncDataKey
import yegor.cheprasov.pokedex.features.sync.data.api.SyncDataState

class HomeViewModel(
    private val syncUseCase: SyncUseCase,
    private val syncStateToModelUiMapper: Mapper<SyncDataState, SyncAllPokemonsStateModelUi>,
) : MviViewModel<HomeStateUi, HomeIntentUi, HomeEffectUi>(
    initialState = HomeStateUi(),
) {

    init {
        Napier.d("Init home viewModel", tag = "myTag")
        syncInitialData()
    }

    override fun onIntent(intent: HomeIntentUi) {
        when (intent) {
            HomeIntentUi.OnSearchClick -> sendEffect(HomeEffectUi.OpenSearchScreen)
            HomeIntentUi.OnRefreshPokemons -> refreshData()
            HomeIntentUi.OnSeeMorePokemonClick -> Unit
            is HomeIntentUi.OnClickMainHomeCard -> onMainHomeCardClick(intent.type)
        }
    }

    private fun onMainHomeCardClick(type: HomeMainCardTypeUi) {
        when (type) {
            HomeMainCardTypeUi.POKEMONS -> sendEffect(HomeEffectUi.OpenPokemonListScreen)
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
