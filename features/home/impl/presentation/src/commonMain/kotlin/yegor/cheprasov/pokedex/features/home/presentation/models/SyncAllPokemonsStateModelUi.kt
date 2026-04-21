package yegor.cheprasov.pokedex.features.home.presentation.models

sealed interface SyncAllPokemonsStateModelUi {
    val percent: Int

    data class Started(
        val total: Int,
    ) : SyncAllPokemonsStateModelUi {
        override val percent: Int = 0
    }

    data class InProgress(
        val completed: Int,
        val total: Int,
    ) : SyncAllPokemonsStateModelUi {
        override val percent: Int = calculatePercent(completed = completed, total = total)
    }

    data class Success(
        val savedCount: Int,
    ) : SyncAllPokemonsStateModelUi {
        override val percent: Int = 100
    }

    data class Error(
        val completed: Int,
        val total: Int,
        val throwable: Throwable,
    ) : SyncAllPokemonsStateModelUi {
        override val percent: Int = calculatePercent(completed = completed, total = total)
    }

    companion object {
        internal fun calculatePercent(
            completed: Int,
            total: Int,
        ): Int = if (total <= 0) 0 else (completed * 100) / total
    }
}