package yegor.cheprasov.pokedex.features.pokemon.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonResponse(
    @SerialName("name")
    val name: String,
    @SerialName("id")
    val id: Int,
    @SerialName("height")
    val height: Int,
    @SerialName("weight")
    val weight: Int,
    @SerialName("base_experience")
    val baseExperience: Int?,
    @SerialName("sprites")
    val sprites: PokemonSpritesResponse,
    @SerialName("types")
    val types: List<PokemonTypeSlotResponse>,
    @SerialName("abilities")
    val abilities: List<PokemonAbilitySlotResponse>,
    @SerialName("stats")
    val stats: List<PokemonStatSlotResponse>,
)

@Serializable
data class PokemonTypeSlotResponse(
    @SerialName("slot")
    val slot: Int,
    @SerialName("type")
    val type: NamedApiResourceResponse,
)

@Serializable
data class NamedApiResourceResponse(
    @SerialName("name")
    val name: String,
)

@Serializable
data class PokemonStatSlotResponse(
    @SerialName("base_stat")
    val baseStat: Int,
    @SerialName("stat")
    val stat: NamedApiResourceResponse,
)

@Serializable
data class PokemonAbilitySlotResponse(
    @SerialName("is_hidden")
    val isHidden: Boolean,
    @SerialName("slot")
    val slot: Int,
    @SerialName("ability")
    val ability: NamedApiResourceResponse,
)
