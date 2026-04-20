package yegor.cheprasov.pokedex.features.pokemon.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonListResponse(
    @SerialName("count")
    val count: Int,
    @SerialName("next")
    val next: String?,
    @SerialName("previous")
    val previous: String?,
    @SerialName("results")
    val results: List<PokemonListItemResult>
)

@Serializable
data class PokemonListItemResult(
    @SerialName("name")
    val name: String
)