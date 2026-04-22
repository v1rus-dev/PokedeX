package yegor.cheprasov.pokedex.features.ability.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AbilityListResponse(
    @SerialName("results")
    val results: List<NamedApiResourceResponse>,
)

@Serializable
data class NamedApiResourceResponse(
    @SerialName("name")
    val name: String,
)
