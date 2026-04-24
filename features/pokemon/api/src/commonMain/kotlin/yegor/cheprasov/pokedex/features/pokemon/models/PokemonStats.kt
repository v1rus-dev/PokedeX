package yegor.cheprasov.pokedex.features.pokemon.models

enum class PokemonStats(
    val rawName: String,
) {
    Hp("hp"),
    Attack("attack"),
    Defense("defense"),
    SpecialAttack("special-attack"),
    SpecialDefense("special-defense"),
    Speed("speed"),
    Unknown("unknown");

    companion object {
        fun fromRawNameOrUnknown(name: String?): PokemonStats {
            return entries.firstOrNull { stat ->
                stat.rawName.equals(other = name, ignoreCase = true)
            } ?: Unknown
        }
    }
}
