package yegor.cheprasov.pokedex.core.database.pokemon.entity

enum class PokemonStatDbModel(
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
        fun fromRawNameOrUnknown(name: String?): PokemonStatDbModel {
            return entries.firstOrNull { stat ->
                stat.rawName.equals(other = name, ignoreCase = true)
            } ?: Unknown
        }
    }
}
