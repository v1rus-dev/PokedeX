package yegor.cheprasov.pokedex.features.home.presentation

import androidx.compose.runtime.Stable
import io.github.v1rusdev.simplemvi.core.EffectUi
import io.github.v1rusdev.simplemvi.core.IntentUi
import io.github.v1rusdev.simplemvi.core.StateUi
import yegor.cheprasov.pokedex.features.home.presentation.models.HomeMainCardTypeUi
import yegor.cheprasov.pokedex.features.home.presentation.models.SyncAllPokemonsStateModelUi

@Stable
data class HomeStateUi(
    val syncAllPokemonsStateModelUi: SyncAllPokemonsStateModelUi? = null,
) : StateUi

sealed interface HomeIntentUi : IntentUi {
    data object OnSearchClick : HomeIntentUi
    data object OnSeeMorePokemonClick : HomeIntentUi
    data object OnRefreshPokemons : HomeIntentUi
    data class OnClickMainHomeCard(val type: HomeMainCardTypeUi) : HomeIntentUi
}

sealed interface HomeEffectUi : EffectUi {
    data object OpenSearchScreen : HomeEffectUi
    data object OpenPokemonListScreen : HomeEffectUi
}
