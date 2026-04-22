package yegor.cheprasov.pokedex.features.ability.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AbilityResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("effect_entries")
    val effectEntries: List<AbilityEffectEntryResponse>,
    @SerialName("pokemon")
    val pokemon: List<AbilityPokemonResponse>,
)

@Serializable
data class AbilityEffectEntryResponse(
    @SerialName("effect")
    val effect: String,
    @SerialName("short_effect")
    val shortEffect: String,
    @SerialName("language")
    val language: NamedApiResourceResponse,
)

@Serializable
data class AbilityPokemonResponse(
    @SerialName("is_hidden")
    val isHidden: Boolean,
    @SerialName("slot")
    val slot: Int,
    @SerialName("pokemon")
    val pokemon: NamedApiResourceResponse,
)
