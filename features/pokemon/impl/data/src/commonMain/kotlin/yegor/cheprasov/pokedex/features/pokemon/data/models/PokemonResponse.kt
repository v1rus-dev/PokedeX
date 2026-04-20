package yegor.cheprasov.pokedex.features.pokemon.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("height")
    val height: Int,
    @SerialName("weight")
    val weight: Int,
    @SerialName("base_experience")
    val baseExperience: Int,
    @SerialName("sprites")
    val sprites: PokemonSpritesResponse,
    @SerialName("types")
    val types: List<PokemonTypeSlotResponse>,
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
