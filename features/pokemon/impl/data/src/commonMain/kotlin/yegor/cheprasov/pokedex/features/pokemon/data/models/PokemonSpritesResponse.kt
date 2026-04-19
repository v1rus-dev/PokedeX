package yegor.cheprasov.pokedex.features.pokemon.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonSpritesResponse(
    @SerialName("back_default")
    val backDefault: String?,
    @SerialName("front_default")
    val frontDefault: String?,
    @SerialName("back_female")
    val backFemale: String?,
    @SerialName("front_female")
    val frontFemale: String?,
)
