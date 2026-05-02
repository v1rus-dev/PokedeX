package yegor.cheprasov.pokedex.features.pokemon.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EvolutionChainListResponse(
    @SerialName("count")
    val count: Int,
    @SerialName("next")
    val next: String?,
    @SerialName("previous")
    val previous: String?,
    @SerialName("results")
    val results: List<EvolutionChainListItemResponse>,
)

@Serializable
data class EvolutionChainListItemResponse(
    @SerialName("url")
    val url: String,
)

@Serializable
data class EvolutionChainResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("chain")
    val chain: EvolutionChainLinkResponse,
)

@Serializable
data class EvolutionChainLinkResponse(
    @SerialName("species")
    val species: NamedApiResourceResponse,
    @SerialName("evolves_to")
    val evolvesTo: List<EvolutionChainLinkResponse> = emptyList(),
)
