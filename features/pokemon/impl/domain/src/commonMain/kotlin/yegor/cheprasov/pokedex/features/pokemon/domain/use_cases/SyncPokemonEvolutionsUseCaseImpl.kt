package yegor.cheprasov.pokedex.features.pokemon.domain.use_cases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import yegor.cheprasov.pokedex.features.pokemon.domain.repository.PokemonRepository
import yegor.cheprasov.pokedex.features.pokemon.use_cases.SyncPokemonEvolutionsUseCase
import yegor.cheprasov.pokedex.features.sync.data.api.SyncDataKey
import yegor.cheprasov.pokedex.features.sync.data.api.SyncDataState

class SyncPokemonEvolutionsUseCaseImpl(
    private val repository: PokemonRepository,
) : SyncPokemonEvolutionsUseCase {
    override val key: SyncDataKey = SyncDataKey.POKEMON_EVOLUTIONS

    override fun invoke(force: Boolean): Flow<SyncDataState> = flow {
        val shouldSync = force || !repository.hasEvolutionChains().getOrDefault(false)
        if (!shouldSync) {
            emit(SyncDataState.Skipped)
            return@flow
        }

        emitAll(repository.syncAllEvolutionChains().map(::mapSyncAllPokemonsState))
    }
}
