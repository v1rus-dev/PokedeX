package yegor.cheprasov.pokedex.features.pokemon.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonResponse(
    @SerialName("name")
    val name: String,
    @SerialName("id")
    val id: Int,
    @SerialName("sprites")
    val sprites: PokemonSpritesResponse
)
