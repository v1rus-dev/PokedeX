package yegor.cheprasov.pokedex.features.ability.models

sealed interface SyncAllAbilitiesState {
    val percent: Int

    data class Started(
        val total: Int,
    ) : SyncAllAbilitiesState {
        override val percent: Int = 0
    }

    data class InProgress(
        val completed: Int,
        val total: Int,
    ) : SyncAllAbilitiesState {
        override val percent: Int = calculatePercent(completed = completed, total = total)
    }

    data class Success(
        val savedCount: Int,
    ) : SyncAllAbilitiesState {
        override val percent: Int = 100
    }

    data class PartialSuccess(
        val savedCount: Int,
        val failedCount: Int,
    ) : SyncAllAbilitiesState {
        override val percent: Int = calculatePercent(
            completed = savedCount,
            total = savedCount + failedCount,
        )
    }

    data class Error(
        val completed: Int,
        val total: Int,
        val throwable: Throwable,
    ) : SyncAllAbilitiesState {
        override val percent: Int = calculatePercent(completed = completed, total = total)
    }

    companion object {
        internal fun calculatePercent(
            completed: Int,
            total: Int,
        ): Int = if (total <= 0) 0 else (completed * 100) / total
    }
}