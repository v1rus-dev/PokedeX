package yegor.cheprasov.pokedex.features.home.domain.use_cases

import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import yegor.cheprasov.pokedex.features.sync.data.api.SyncDataKey
import yegor.cheprasov.pokedex.features.sync.data.api.SyncDataState
import yegor.cheprasov.pokedex.features.sync.data.api.SyncDataUseCase

interface SyncUseCase {
    operator fun invoke(force: Boolean = false): Flow<Map<SyncDataKey, SyncDataState>>
}

class SyncUseCaseImpl(
    private val syncUseCases: List<SyncDataUseCase>,
) : SyncUseCase {
    override fun invoke(force: Boolean): Flow<Map<SyncDataKey, SyncDataState>> {
        if (syncUseCases.isEmpty()) {
            return flowOf(emptyMap())
        }

        Napier.v("Sync use cases: $syncUseCases", tag = "myTag")

        return combine(
            flows = syncUseCases.map { syncDataUseCase ->
                syncDataUseCase.invoke(force = force).map { state ->
                    syncDataUseCase.key to state
                }
            },
        ) { states ->
            states.toMap()
        }
    }
}
