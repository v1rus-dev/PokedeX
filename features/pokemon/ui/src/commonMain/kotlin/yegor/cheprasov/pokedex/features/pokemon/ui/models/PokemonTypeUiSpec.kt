package yegor.cheprasov.pokedex.features.pokemon.ui.models

import org.jetbrains.compose.resources.StringResource
import pokedex.core.resources.generated.resources.Res
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
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonType

data class PokemonTypeUiSpec(
    val labelRes: StringResource,
)

fun PokemonType.toUiSpec(): PokemonTypeUiSpec {
    return PokemonTypeUiSpec(
        labelRes = labelRes,
    )
}

val PokemonType.labelRes: StringResource
    get() = when (this) {
        PokemonType.Normal -> Res.string.type_normal
        PokemonType.Fighting -> Res.string.type_fighting
        PokemonType.Flying -> Res.string.type_flying
        PokemonType.Poison -> Res.string.type_poison
        PokemonType.Ground -> Res.string.type_ground
        PokemonType.Rock -> Res.string.type_rock
        PokemonType.Bug -> Res.string.type_bug
        PokemonType.Ghost -> Res.string.type_ghost
        PokemonType.Steel -> Res.string.type_steel
        PokemonType.Fire -> Res.string.type_fire
        PokemonType.Water -> Res.string.type_water
        PokemonType.Grass -> Res.string.type_grass
        PokemonType.Electric -> Res.string.type_electric
        PokemonType.Psychic -> Res.string.type_psychic
        PokemonType.Ice -> Res.string.type_ice
        PokemonType.Dragon -> Res.string.type_dragon
        PokemonType.Dark -> Res.string.type_dark
        PokemonType.Fairy -> Res.string.type_fairy
        PokemonType.Stellar -> Res.string.type_stellar
        PokemonType.Unknown -> Res.string.type_unknown
    }
