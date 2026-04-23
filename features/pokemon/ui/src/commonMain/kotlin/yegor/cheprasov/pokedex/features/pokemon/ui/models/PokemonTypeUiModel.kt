package yegor.cheprasov.pokedex.features.pokemon.ui.models

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import pokedex.core.resources.generated.resources.Res
import pokedex.core.resources.generated.resources.ic_bug_type_icon
import pokedex.core.resources.generated.resources.ic_dark_type_icon
import pokedex.core.resources.generated.resources.ic_dragon_type_icon
import pokedex.core.resources.generated.resources.ic_electric_type_icon
import pokedex.core.resources.generated.resources.ic_fairy_type_icon
import pokedex.core.resources.generated.resources.ic_fighting_type_icon
import pokedex.core.resources.generated.resources.ic_fire_type_icon
import pokedex.core.resources.generated.resources.ic_flying_type_icon
import pokedex.core.resources.generated.resources.ic_ghost_type_icon
import pokedex.core.resources.generated.resources.ic_grass_type_icon
import pokedex.core.resources.generated.resources.ic_ground_type_icon
import pokedex.core.resources.generated.resources.ic_ice_type_icon
import pokedex.core.resources.generated.resources.ic_normal_type_icon
import pokedex.core.resources.generated.resources.ic_poison_type_icon
import pokedex.core.resources.generated.resources.ic_psychic_type_icon
import pokedex.core.resources.generated.resources.ic_rock_type_icon
import pokedex.core.resources.generated.resources.ic_steel_type_icon
import pokedex.core.resources.generated.resources.ic_stellar_type_icon
import pokedex.core.resources.generated.resources.ic_unknown_type_icon
import pokedex.core.resources.generated.resources.ic_water_type_icon
import pokedex.core.resources.generated.resources.type_bug
import pokedex.core.resources.generated.resources.type_dark
import pokedex.core.resources.generated.resources.type_dragon
import pokedex.core.resources.generated.resources.type_electric
import pokedex.core.resources.generated.resources.type_fairy
import pokedex.core.resources.generated.resources.type_fighting
import pokedex.core.resources.generated.resources.type_fire
import pokedex.core.resources.generated.resources.type_flying
import pokedex.core.resources.generated.resources.type_ghost
import pokedex.core.resources.generated.resources.type_grass
import pokedex.core.resources.generated.resources.type_ground
import pokedex.core.resources.generated.resources.type_ice
import pokedex.core.resources.generated.resources.type_normal
import pokedex.core.resources.generated.resources.type_poison
import pokedex.core.resources.generated.resources.type_psychic
import pokedex.core.resources.generated.resources.type_rock
import pokedex.core.resources.generated.resources.type_steel
import pokedex.core.resources.generated.resources.type_stellar
import pokedex.core.resources.generated.resources.type_unknown
import pokedex.core.resources.generated.resources.type_water

@Immutable
data class PokemonTypeColors(
    val primary: Color,
    val gradientStart: Color,
    val gradientEnd: Color,
    val container: Color,
    val onPrimary: Color,
)

@Immutable
enum class PokemonTypeUiModel(
    val label: StringResource,
    val icon: DrawableResource,
    val colors: PokemonTypeColors,
) {
    Normal(
        Res.string.type_normal,
        Res.drawable.ic_normal_type_icon,
        PokemonTypeColors(
            primary = Color(0xFF9CA3AF),
            gradientStart = Color(0xFFB8BFC8),
            gradientEnd = Color(0xFF8B95A1),
            container = Color(0xFFE5E7EB),
            onPrimary = Color.Black,
        ),
    ),
    Fighting(
        Res.string.type_fighting,
        Res.drawable.ic_fighting_type_icon,
        PokemonTypeColors(
            primary = Color(0xFFEF4444),
            gradientStart = Color(0xFFFF6B6B),
            gradientEnd = Color(0xFFD92D20),
            container = Color(0xFFFFE2E2),
            onPrimary = Color.White,
        ),
    ),
    Flying(
        Res.string.type_flying,
        Res.drawable.ic_flying_type_icon,
        PokemonTypeColors(
            primary = Color(0xFF60A5FA),
            gradientStart = Color(0xFF8EC5FF),
            gradientEnd = Color(0xFF3B82F6),
            container = Color(0xFFE0F2FE),
            onPrimary = Color.White,
        ),
    ),
    Poison(
        Res.string.type_poison,
        Res.drawable.ic_poison_type_icon,
        PokemonTypeColors(
            primary = Color(0xFFA855F7),
            gradientStart = Color(0xFFC084FC),
            gradientEnd = Color(0xFF7E22CE),
            container = Color(0xFFF3E8FF),
            onPrimary = Color.White,
        ),
    ),
    Ground(
        Res.string.type_ground,
        Res.drawable.ic_ground_type_icon,
        PokemonTypeColors(
            primary = Color(0xFFD4A373),
            gradientStart = Color(0xFFE7BC91),
            gradientEnd = Color(0xFFB08968),
            container = Color(0xFFFAE7D6),
            onPrimary = Color.Black,
        ),
    ),
    Rock(
        Res.string.type_rock,
        Res.drawable.ic_rock_type_icon,
        PokemonTypeColors(
            primary = Color(0xFF78716C),
            gradientStart = Color(0xFF9A938D),
            gradientEnd = Color(0xFF57534E),
            container = Color(0xFFE7E5E4),
            onPrimary = Color.White,
        ),
    ),
    Bug(
        Res.string.type_bug,
        Res.drawable.ic_bug_type_icon,
        PokemonTypeColors(
            primary = Color(0xFF84CC16),
            gradientStart = Color(0xFFA3E635),
            gradientEnd = Color(0xFF65A30D),
            container = Color(0xFFECFCCB),
            onPrimary = Color.Black,
        ),
    ),
    Ghost(
        Res.string.type_ghost,
        Res.drawable.ic_ghost_type_icon,
        PokemonTypeColors(
            primary = Color(0xFF6366F1),
            gradientStart = Color(0xFF818CF8),
            gradientEnd = Color(0xFF4338CA),
            container = Color(0xFFE0E7FF),
            onPrimary = Color.White,
        ),
    ),
    Steel(
        Res.string.type_steel,
        Res.drawable.ic_steel_type_icon,
        PokemonTypeColors(
            primary = Color(0xFF94A3B8),
            gradientStart = Color(0xFFB6C2D1),
            gradientEnd = Color(0xFF64748B),
            container = Color(0xFFE2E8F0),
            onPrimary = Color.Black,
        ),
    ),
    Fire(
        Res.string.type_fire,
        Res.drawable.ic_fire_type_icon,
        PokemonTypeColors(
            primary = Color(0xFFF97316),
            gradientStart = Color(0xFFFF9A3D),
            gradientEnd = Color(0xFFEA580C),
            container = Color(0xFFFFEDD5),
            onPrimary = Color.White,
        ),
    ),
    Water(
        Res.string.type_water,
        Res.drawable.ic_water_type_icon,
        PokemonTypeColors(
            primary = Color(0xFF3B82F6),
            gradientStart = Color(0xFF60A5FA),
            gradientEnd = Color(0xFF1D4ED8),
            container = Color(0xFFDBEAFE),
            onPrimary = Color.White,
        ),
    ),
    Grass(
        Res.string.type_grass,
        Res.drawable.ic_grass_type_icon,
        PokemonTypeColors(
            primary = Color(0xFF22C55E),
            gradientStart = Color(0xFF4ADE80),
            gradientEnd = Color(0xFF15803D),
            container = Color(0xFFDCFCE7),
            onPrimary = Color.White,
        ),
    ),
    Electric(
        Res.string.type_electric,
        Res.drawable.ic_electric_type_icon,
        PokemonTypeColors(
            primary = Color(0xFFFACC15),
            gradientStart = Color(0xFFFDE047),
            gradientEnd = Color(0xFFEAB308),
            container = Color(0xFFFEF9C3),
            onPrimary = Color.Black,
        ),
    ),
    Psychic(
        Res.string.type_psychic,
        Res.drawable.ic_psychic_type_icon,
        PokemonTypeColors(
            primary = Color(0xFFEC4899),
            gradientStart = Color(0xFFF472B6),
            gradientEnd = Color(0xFFDB2777),
            container = Color(0xFFFCE7F3),
            onPrimary = Color.White,
        ),
    ),
    Ice(
        Res.string.type_ice,
        Res.drawable.ic_ice_type_icon,
        PokemonTypeColors(
            primary = Color(0xFF67E8F9),
            gradientStart = Color(0xFFA5F3FC),
            gradientEnd = Color(0xFF06B6D4),
            container = Color(0xFFCFFAFE),
            onPrimary = Color.Black,
        ),
    ),
    Dragon(
        Res.string.type_dragon,
        Res.drawable.ic_dragon_type_icon,
        PokemonTypeColors(
            primary = Color(0xFF7C3AED),
            gradientStart = Color(0xFFA78BFA),
            gradientEnd = Color(0xFF5B21B6),
            container = Color(0xFFEDE9FE),
            onPrimary = Color.White,
        ),
    ),
    Dark(
        Res.string.type_dark,
        Res.drawable.ic_dark_type_icon,
        PokemonTypeColors(
            primary = Color(0xFF4B5563),
            gradientStart = Color(0xFF6B7280),
            gradientEnd = Color(0xFF1F2937),
            container = Color(0xFFE5E7EB),
            onPrimary = Color.White,
        ),
    ),
    Fairy(
        Res.string.type_fairy,
        Res.drawable.ic_fairy_type_icon,
        PokemonTypeColors(
            primary = Color(0xFFF9A8D4),
            gradientStart = Color(0xFFFBCFE8),
            gradientEnd = Color(0xFFEC4899),
            container = Color(0xFFFDF2F8),
            onPrimary = Color.Black,
        ),
    ),
    Stellar(
        Res.string.type_stellar,
        Res.drawable.ic_stellar_type_icon,
        PokemonTypeColors(
            primary = Color(0xFF38BDF8),
            gradientStart = Color(0xFF7DD3FC),
            gradientEnd = Color(0xFF2563EB),
            container = Color(0xFFE0F2FE),
            onPrimary = Color.White,
        ),
    ),
    Unknown(
        Res.string.type_unknown,
        Res.drawable.ic_unknown_type_icon,
        PokemonTypeColors(
            primary = Color(0xFF9CA3AF),
            gradientStart = Color(0xFFD1D5DB),
            gradientEnd = Color(0xFF6B7280),
            container = Color(0xFFF3F4F6),
            onPrimary = Color.Black,
        ),
    ),
}
