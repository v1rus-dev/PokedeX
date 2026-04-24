package yegor.cheprasov.pokedex.features.pokemon.ui.models

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.StringResource
import pokedex.core.resources.generated.resources.Res
import pokedex.core.resources.generated.resources.stat_attack
import pokedex.core.resources.generated.resources.stat_defense
import pokedex.core.resources.generated.resources.stat_hp
import pokedex.core.resources.generated.resources.stat_special_attack
import pokedex.core.resources.generated.resources.stat_special_defense
import pokedex.core.resources.generated.resources.stat_speed
import pokedex.core.resources.generated.resources.stat_unknown

@Immutable
enum class PokemonStatsUiModel(
    val label: StringResource,
    val color: Color,
) {
    Hp(
        Res.string.stat_hp,
        Color(0xFF22C55E),
    ),
    Attack(
        Res.string.stat_attack,
        Color(0xFFEF4444),
    ),
    Defense(
        Res.string.stat_defense,
        Color(0xFF3B82F6),
    ),
    SpecialAttack(
        Res.string.stat_special_attack,
        Color(0xFFA855F7),
    ),
    SpecialDefense(
        Res.string.stat_special_defense,
        Color(0xFF14B8A6),
    ),
    Speed(
        Res.string.stat_speed,
        Color(0xFFF59E0B),
    ),
    Unknown(
        Res.string.stat_unknown,
        Color(0xFF9CA3AF),
    ),
}
