package yegor.cheprasov.pokedex.features.home.presentation

import androidx.lifecycle.viewModelScope
import io.github.aakira.napier.Napier
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import yegor.cheprasov.pokedex.core.common.mapper.Mapper
import yegor.cheprasov.pokedex.core.design.mvi.MviViewModel
import yegor.cheprasov.pokedex.core.network.asResult
import yegor.cheprasov.pokedex.features.ability.data.models.AbilityLocalModel
import yegor.cheprasov.pokedex.features.ability.use_cases.HasAbilitiesInDatabaseUseCase
import yegor.cheprasov.pokedex.features.home.presentation.models.SyncAllPokemonsStateModelUi
import yegor.cheprasov.pokedex.features.pokemon.models.SyncAllPokemonsState
import yegor.cheprasov.pokedex.features.pokemon.use_cases.HasPokemonsInDatabaseUseCase
import yegor.cheprasov.pokedex.features.pokemon.use_cases.SyncPokemonsUseCase

class HomeViewModel(
    private val hasPokemonsInDatabaseUseCase: HasPokemonsInDatabaseUseCase,
    private val hasAbilitiesInDatabaseUseCase: HasAbilitiesInDatabaseUseCase,
    private val syncPokemonsUseCase: SyncPokemonsUseCase,
    private val syncAllPokemonsStateToModelUiMapper: Mapper<SyncAllPokemonsState, SyncAllPokemonsStateModelUi>,
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
        }
    }

    private fun syncInitialData() {
        viewModelScope.launch {
            if (!hasPokemonsInDatabaseUseCase()) {
                syncPokemons()
            }
        }

        viewModelScope.launch {
            if (!hasAbilitiesInDatabaseUseCase().getOrDefault(false)) {
                syncAbilitiesIfNeeded()
            }
        }
    }

    private fun refreshData() {
        viewModelScope.launch {
            syncPokemons()
        }
    }

    private suspend fun syncPokemons() {
        syncPokemonsUseCase().map(syncAllPokemonsStateToModelUiMapper::map)
            .collectLatest { state ->
                Napier.d("Sync pokemon use case: $state", tag = "myTag")
                updateState { copy(syncAllPokemonsStateModelUi = state) }
            }
    }

    private suspend fun syncAbilitiesIfNeeded() {
        val existingAbilities = abilityRepository.getAllAbilities().getOrDefault(emptyList())
        if (existingAbilities.size >= ABILITIES_LIMIT) return

        runCatching {
            val abilityNames = abilityNetworkDatasource.getAllAbilityList(ABILITIES_LIMIT)
                .asResult()
                .getOrThrow()
                .results
                .map { it.name.lowercase() }

            val abilities = mutableListOf<AbilityLocalModel>()
            for (batch in abilityNames.chunked(MAX_CONCURRENT_REQUESTS)) {
                val batchAbilities = coroutineScope {
                    batch.map { abilityName ->
                        async {
                            abilityNetworkDatasource.getAbility(abilityName)
                                .asResult()
                                .map(abilityResponseMapper::map)
                                .getOrThrow()
                        }
                    }.awaitAll()
                }
                abilities += batchAbilities
            }

            abilityRepository.replaceAllAbilities(abilities).getOrThrow()
        }.onFailure { throwable ->
            Napier.e("Failed to sync abilities", throwable, tag = "myTag")
        }
    }

    private companion object {
        const val ABILITIES_LIMIT = 500
        const val MAX_CONCURRENT_REQUESTS = 64
    }
}
