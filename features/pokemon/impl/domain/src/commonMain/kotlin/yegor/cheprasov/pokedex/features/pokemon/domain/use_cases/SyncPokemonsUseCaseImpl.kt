package yegor.cheprasov.pokedex.features.pokemon.domain.use_cases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import yegor.cheprasov.pokedex.features.pokemon.domain.repository.PokemonRepository
import yegor.cheprasov.pokedex.features.pokemon.use_cases.SyncPokemonsUseCase
import yegor.cheprasov.pokedex.features.sync.data.api.SyncDataKey
import yegor.cheprasov.pokedex.features.sync.data.api.SyncDataState

class SyncPokemonsUseCaseImpl(
    private val repository: PokemonRepository,
) : SyncPokemonsUseCase {
    override val key: SyncDataKey = SyncDataKey.POKEMONS

    override fun invoke(force: Boolean): Flow<SyncDataState> = flow {
        val shouldSync = force || !repository.hasPokemons().getOrDefault(false)
        if (!shouldSync) {
            emit(SyncDataState.Skipped)
            return@flow
        }

        emitAll(repository.syncAllPokemons().map(::mapState))
    }

    private fun mapState(state: yegor.cheprasov.pokedex.features.pokemon.models.SyncAllPokemonsState): SyncDataState = when (state) {
        is yegor.cheprasov.pokedex.features.pokemon.models.SyncAllPokemonsState.Error ->
            SyncDataState.Error(
                completed = state.completed,
                total = state.total,
                throwable = state.throwable,
            )

        is yegor.cheprasov.pokedex.features.pokemon.models.SyncAllPokemonsState.InProgress ->
            SyncDataState.InProgress(
                completed = state.completed,
                total = state.total,
            )

        is yegor.cheprasov.pokedex.features.pokemon.models.SyncAllPokemonsState.PartialSuccess ->
            SyncDataState.PartialSuccess(
                savedCount = state.savedCount,
                failedCount = state.failedCount,
            )

        is yegor.cheprasov.pokedex.features.pokemon.models.SyncAllPokemonsState.Started ->
            SyncDataState.Started(total = state.total)

        is yegor.cheprasov.pokedex.features.pokemon.models.SyncAllPokemonsState.Success ->
            SyncDataState.Success(savedCount = state.savedCount)
    }
}
