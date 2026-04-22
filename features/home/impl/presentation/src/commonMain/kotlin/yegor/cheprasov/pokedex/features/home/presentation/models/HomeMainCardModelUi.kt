package yegor.cheprasov.pokedex.features.home.presentation.models

import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import pokedex.core.resources.generated.resources.Res
import pokedex.core.resources.generated.resources.abilities
import pokedex.core.resources.generated.resources.ic_abilities_bg_icon
import pokedex.core.resources.generated.resources.ic_location_bg_icon
import pokedex.core.resources.generated.resources.ic_pokeball_bg_icon
import pokedex.core.resources.generated.resources.location
import pokedex.core.resources.generated.resources.pokemons

data class HomeMainCardModelUi(
    val type: HomeMainCardTypeUi,
    val label: StringResource,
    val icon: DrawableResource,
    val backgroundColor: Color,
    val iconRotation: Float = -18f,
) {
    companion object {
        val items = listOf(
            HomeMainCardModelUi(
                type = HomeMainCardTypeUi.POKEMONS,
                label = Res.string.pokemons,
                icon = Res.drawable.ic_pokeball_bg_icon,
                backgroundColor = Color(0xFFDC0A2D),
            ),
            HomeMainCardModelUi(
                type = HomeMainCardTypeUi.ABILITIES,
                label = Res.string.abilities,
                icon = Res.drawable.ic_abilities_bg_icon,
                backgroundColor = Color(0xFF5B3A8A),
            ),
            HomeMainCardModelUi(
                type = HomeMainCardTypeUi.LOCATIONS,
                label = Res.string.location,
                icon = Res.drawable.ic_location_bg_icon,
                backgroundColor = Color(0xFF3F7D4E),
            ),
        )
    }
}

enum class HomeMainCardTypeUi {
    POKEMONS,
    ABILITIES,
    LOCATIONS,
}
