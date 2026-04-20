package yegor.cheprasov.pokedex.features.home.presentation

import androidx.lifecycle.viewModelScope
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import yegor.cheprasov.pokedex.core.design.mvi.MviViewModel
import yegor.cheprasov.pokedex.features.pokemon.use_cases.HasPokemonsInDatabaseUseCase
import yegor.cheprasov.pokedex.features.pokemon.use_cases.SyncPokemonsUseCase

class HomeViewModel(
    private val hasPokemonsInDatabaseUseCase: HasPokemonsInDatabaseUseCase,
    private val syncPokemonsUseCase: SyncPokemonsUseCase
) : MviViewModel<HomeStateUi, HomeActionUi, HomeEventUi>(
    initialState = HomeStateUi,
) {

    init {
        syncPokemons()
    }


    private fun syncPokemons() {
        viewModelScope.launch {
            if (hasPokemonsInDatabaseUseCase()) return@launch
            syncPokemonsUseCase().collectLatest { state ->
                Napier.d("Sync pokemon use case: $state", tag = "myTag")
            }
        }
    }

    override fun onAction(action: HomeActionUi) {
        when (action) {
            HomeActionUi.OnSearchClick -> sendEvent(HomeEventUi.OpenSearchScreen)
        }
    }
}
