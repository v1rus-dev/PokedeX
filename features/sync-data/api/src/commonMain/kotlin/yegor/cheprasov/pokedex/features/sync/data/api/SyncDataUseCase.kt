package yegor.cheprasov.pokedex.features.sync.data.api

import kotlinx.coroutines.flow.Flow

interface SyncDataUseCase {
    val key: SyncDataKey

    operator fun invoke(force: Boolean = false): Flow<SyncDataState>
}
