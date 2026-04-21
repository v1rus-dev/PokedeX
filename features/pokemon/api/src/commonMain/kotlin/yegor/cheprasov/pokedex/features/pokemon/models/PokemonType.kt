package yegor.cheprasov.pokedex.features.pokemon.models

enum class PokemonType {
    Normal,
    Fighting,
    Flying,
    Poison,
    Ground,
    Rock,
    Bug,
    Ghost,
    Steel,
    Fire,
    Water,
    Grass,
    Electric,
    Psychic,
    Ice,
    Dragon,
    Dark,
    Fairy,
    Stellar,
    Unknown;

    companion object {
        fun fromRawNameOrUnknown(name: String?): PokemonType {
            return entries.firstOrNull { type ->
                type.name.equals(other = name, ignoreCase = true)
            } ?: Unknown
        }

        fun fromRawName(name: String?): PokemonType? {
            return entries.firstOrNull { type ->
                type.name.equals(other = name, ignoreCase = true)
            }
        }
    }
}
