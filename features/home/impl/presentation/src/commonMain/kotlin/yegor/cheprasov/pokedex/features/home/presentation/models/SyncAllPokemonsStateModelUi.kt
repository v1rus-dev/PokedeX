package yegor.cheprasov.pokedex.features.home.presentation.models

import yegor.cheprasov.pokedex.features.pokemon.models.SyncAllPokemonsState

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
        override val percent: Int = calculatePercent(
            completed = completed,
            total = total,
        )
    }

    data class Success(
        val savedCount: Int,
    ) : SyncAllPokemonsStateModelUi {
        override val percent: Int = 100
    }

    data class PartialSuccess(
        val savedCount: Int,
        val failedCount: Int,
    ) : SyncAllPokemonsStateModelUi {
        override val percent: Int = calculatePercent(
            completed = savedCount,
            total = savedCount + failedCount,
        )
    }

    data class Error(
        val completed: Int,
        val total: Int,
        val throwable: Throwable,
    ) : SyncAllPokemonsStateModelUi {
        override val percent: Int = calculatePercent(
            completed = completed,
            total = total,
        )
    }

    companion object {
        private fun calculatePercent(
            completed: Int,
            total: Int,
        ): Int = if (total <= 0) 0 else (completed * 100) / total
    }
}
