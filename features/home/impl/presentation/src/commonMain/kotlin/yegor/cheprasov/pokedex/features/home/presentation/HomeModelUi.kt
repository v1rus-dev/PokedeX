package yegor.cheprasov.pokedex.features.home.presentation

import androidx.compose.runtime.Stable
import yegor.cheprasov.pokedex.core.design.mvi.ActionUi
import yegor.cheprasov.pokedex.core.design.mvi.EventUi
import yegor.cheprasov.pokedex.core.design.mvi.StateUi
import yegor.cheprasov.pokedex.features.home.presentation.models.SyncAllPokemonsStateModelUi

@Stable
data class HomeStateUi(
    val syncAllPokemonsStateModelUi: SyncAllPokemonsStateModelUi? = null,
) : StateUi

sealed interface HomeActionUi : ActionUi {
    data object OnSearchClick : HomeActionUi

    data object OnRefreshPokemons : HomeActionUi
}

sealed interface HomeEventUi : EventUi {
    data object OpenSearchScreen : HomeEventUi
}
