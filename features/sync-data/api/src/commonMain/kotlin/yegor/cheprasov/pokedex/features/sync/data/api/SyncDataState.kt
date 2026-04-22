package yegor.cheprasov.pokedex.features.sync.data.api

sealed interface SyncDataState {
    val percent: Int

    data object Skipped : SyncDataState {
        override val percent: Int = 100
    }

    data class Started(
        val total: Int,
    ) : SyncDataState {
        override val percent: Int = 0
    }

    data class InProgress(
        val completed: Int,
        val total: Int,
    ) : SyncDataState {
        override val percent: Int = calculatePercent(completed = completed, total = total)
    }

    data class Success(
        val savedCount: Int,
    ) : SyncDataState {
        override val percent: Int = 100
    }

    data class PartialSuccess(
        val savedCount: Int,
        val failedCount: Int,
    ) : SyncDataState {
        override val percent: Int = calculatePercent(
            completed = savedCount,
            total = savedCount + failedCount,
        )
    }

    data class Error(
        val completed: Int,
        val total: Int,
        val throwable: Throwable,
    ) : SyncDataState {
        override val percent: Int = calculatePercent(completed = completed, total = total)
    }

    companion object {
        internal fun calculatePercent(
            completed: Int,
            total: Int,
        ): Int = if (total <= 0) 0 else (completed * 100) / total
    }
}
