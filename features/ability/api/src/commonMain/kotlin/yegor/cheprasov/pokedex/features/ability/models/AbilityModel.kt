package yegor.cheprasov.pokedex.features.ability.models

data class AbilityModel(
    val id: Int,
    val name: String,
    val effect: String?,
    val shortEffect: String?,
)
